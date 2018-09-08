package com.app.retail.config.cassandra;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Order(101)
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cassandra")
@Validated
public class CassandraProps {
  @NotEmpty
  private List<String>                contactpoints                          = new ArrayList<>();
  private int                         port                                   = 9042;
  @NotBlank
  private String                      username;
  @NotBlank
  private String                      keyspace;
  @NotNull
  private ProtocolVersion             protocolVersion                        = ProtocolVersion.V4;
  // dc and load balancing
  private String                      localDc;
  private int                         usedHostsPerRemoteDc                   = 0;
  private boolean                     allowRemoteDCsForLocalConsistencyLevel = false;
  // socket options
  private boolean                     keepSocketAlive                        = true;
  // pooling options
  private int                         localCoreConnections                   = 1;
  private int                         localMaxConnections                    = 8;
  private int                         localMaxSimultaneousRequests           = 2560;

  @NotNull
  private ReconnectProps              reconnect                              = new ReconnectProps();
  @NotNull
  private RetryProps                  retry                                  = new RetryProps();
  @NotNull
  private SSLProps                    ssl                                    = new SSLProps();
  @NotNull
  private ProtocolOptions.Compression compression                            = ProtocolOptions.Compression.NONE;
  private boolean                     defaultIdempotence                     = true;
  @NotNull
  private ConsistencyLevel            defaultConsistencyLevel                = ConsistencyLevel.LOCAL_QUORUM;
  @NotNull
  private ConsistencyLevel            defaultSerialConsistencyLevel          = ConsistencyLevel.LOCAL_SERIAL;
  @NotNull
  private SpeculativeExecutionProps   speculativeExecution                   = new SpeculativeExecutionProps();

  @Getter
  @Setter
  public static class ReconnectProps {
    private int baseDelay = 1000;
    private int maxDelay  = 600000;
  }

  @Getter
  @Setter
  public static class RetryProps {
    private boolean enableDowngradingConsistencyRetryPolicy = false;
  }

  @Setter
  public static class SSLProps {
    private boolean env = false;

    public boolean isEnabled() {
      return env;
    }
  }


  @Getter
  @Setter
  @ToString(exclude = "enabled")
  public static class SpeculativeExecutionProps {
    private boolean enabled                  = false;
    @Min(0)
    @DecimalMax(value = "100", inclusive = false)
    private double  percentile               = 99.0;
    @Min(1)
    private int     maxSpeculativeExecutions = 2;
  }
}
