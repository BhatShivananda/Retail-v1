package com.app.retail.dao;


import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.app.retail.dao", "com.app.retail.config.cassandra"})
@PropertySource(value = {"classpath:cassandra.embedded.properties"})
@ImportAutoConfiguration({ConfigurationPropertiesAutoConfiguration.class})
public class CassandraTestConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
