package com.app.retail.config.cassandra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "cassandra")
@Getter
@Setter
@Component
public class CassandraPasswordFromProps implements CassandraPassword {
  private String password = "NOT_SET";
}
