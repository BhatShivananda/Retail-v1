package com.app.retail.config.feign;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignLoggerFactory;

import feign.Logger;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RetailFeignLoggerFactory implements FeignLoggerFactory {

  @Override
  public Logger create(Class<?> type) {
    return new RetailFeignSlf4jLogger(LoggerFactory.getLogger(type));
  }
}
