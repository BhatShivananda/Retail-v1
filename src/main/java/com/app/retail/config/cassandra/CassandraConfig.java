package com.app.retail.config.cassandra;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.*;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.policies.*;
import com.datastax.driver.mapping.MappingManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CassandraConfig {

  private final CassandraProps    cassandraProps;
  private final CassandraPassword cassandraPassword;

  @Bean
  protected Cluster cluster() throws Exception {
    Builder builder = Cluster.builder();

    DCAwareRoundRobinPolicy.Builder childLbBuilder =
        DCAwareRoundRobinPolicy.builder().withUsedHostsPerRemoteDc(cassandraProps.getUsedHostsPerRemoteDc());
    if (cassandraProps.isAllowRemoteDCsForLocalConsistencyLevel()) {
      LOGGER.info("Allow remote DCs for local consistency level is true");
      childLbBuilder.allowRemoteDCsForLocalConsistencyLevel();
    }
    String localDc = cassandraProps.getLocalDc();
    if (StringUtils.isNotBlank(localDc)) {
      LOGGER.info("connecting to cassandra with local dc: {}", localDc);
      childLbBuilder.withLocalDc(localDc);
    }
    List<String> contactPoints = cassandraProps.getContactpoints();
    LOGGER.info("connecting to cassandra with contact points: {}", contactPoints);
    contactPoints.forEach(builder::addContactPoint);
    TokenAwarePolicy lbPolicy = new TokenAwarePolicy(childLbBuilder.build());
    builder.withLoadBalancingPolicy(lbPolicy);
    builder.withSocketOptions(socketOptions());
    builder.withPoolingOptions(poolingOptions());
    builder.withQueryOptions(queryOptions());
    builder.withPort(cassandraProps.getPort());
    builder.withProtocolVersion(cassandraProps.getProtocolVersion());
    AuthProvider authProvider =
        new PlainTextAuthProvider(cassandraProps.getUsername(), cassandraPassword.getPassword());
    builder.withAuthProvider(authProvider);
    if (cassandraProps.getSsl().isEnabled()) {
      LOGGER.info("Cassandra SSL enabled");
      builder.withSSL();
    }
    builder.withReconnectionPolicy(reconnectionPolicy());
    builder.withRetryPolicy(new LoggingRetryPolicy(retryPolicy()));
    ProtocolOptions.Compression compression = cassandraProps.getCompression();
    LOGGER.info("Using {} compression", compression);
    builder.withCompression(compression);
    if (cassandraProps.getSpeculativeExecution().isEnabled()) {
      LOGGER.info("Using speculativeExecutionPolicy: {}", cassandraProps.getSpeculativeExecution());
      builder.withSpeculativeExecutionPolicy(speculativeExecutionPolicy());
    }
    return builder.build();
  }

  @Bean
  protected QueryOptions queryOptions() {
    QueryOptions options = new QueryOptions();
    ConsistencyLevel consistencyLevel = cassandraProps.getDefaultConsistencyLevel();
    LOGGER.info("Default consistencyLevel: {}", consistencyLevel);
    options.setConsistencyLevel(consistencyLevel);
    ConsistencyLevel serialConsistencyLevel = cassandraProps.getDefaultSerialConsistencyLevel();
    LOGGER.info("Default serialConsistencyLevel: {}", serialConsistencyLevel);
    options.setSerialConsistencyLevel(serialConsistencyLevel);
    boolean defaultIdempotence = cassandraProps.isDefaultIdempotence();
    LOGGER.info("defaultIdempotence: {}", defaultIdempotence);
    options.setDefaultIdempotence(defaultIdempotence);
    return options;
  }


  @Bean
  protected PoolingOptions poolingOptions() {
    PoolingOptions options = new PoolingOptions();
    int localCoreConnections = cassandraProps.getLocalCoreConnections();
    int localMaxConnections = cassandraProps.getLocalMaxConnections();
    int localMaxSimultaneousRequests = cassandraProps.getLocalMaxSimultaneousRequests();
    LOGGER.info("Core connections per LOCAL host: {}", localCoreConnections);
    options.setCoreConnectionsPerHost(HostDistance.LOCAL, localCoreConnections);
    LOGGER.info("Max connections per LOCAL host: {}", localMaxConnections);
    options.setMaxConnectionsPerHost(HostDistance.LOCAL, localMaxConnections);
    LOGGER.info("Max request per connection for LOCAL host: {}", localMaxSimultaneousRequests);
    options.setMaxRequestsPerConnection(HostDistance.LOCAL, localMaxSimultaneousRequests);
    return options;
  }


  @Bean
  protected SocketOptions socketOptions() {
    SocketOptions options = new SocketOptions();
    options.setKeepAlive(cassandraProps.isKeepSocketAlive());
    return options;
  }


  @Bean
  protected Session session() throws Exception {
    return cluster().connect(cassandraProps.getKeyspace());
  }


  @Bean
  protected ReconnectionPolicy reconnectionPolicy() {
    LOGGER.info("ReconnectionPolicy = {}(baseDelay: {})", ConstantReconnectionPolicy.class.getCanonicalName(),
        cassandraProps.getReconnect().getBaseDelay());
    return new ConstantReconnectionPolicy(cassandraProps.getReconnect().getBaseDelay());
  }


  @Bean
  protected RetryPolicy retryPolicy() {
    RetryPolicy retryPolicy = DefaultRetryPolicy.INSTANCE;
    LOGGER.info("Using: {}", retryPolicy.getClass().getCanonicalName());
    return retryPolicy;
  }

  @Bean
  protected SpeculativeExecutionPolicy speculativeExecutionPolicy() {
    PercentileTracker tracker =
        ClusterWidePercentileTracker.builder(socketOptions().getReadTimeoutMillis() + 1000).build();
    return new PercentileSpeculativeExecutionPolicy(tracker, cassandraProps.getSpeculativeExecution().getPercentile(),
        cassandraProps.getSpeculativeExecution().getMaxSpeculativeExecutions());
  }


  @Bean
  public MappingManager mappingManager() throws Exception {
    return new MappingManager(session());
  }

}
