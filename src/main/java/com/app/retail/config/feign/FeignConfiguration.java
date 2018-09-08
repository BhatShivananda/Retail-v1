package com.app.retail.config.feign;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FeignConfiguration {

  @Value("${feign.maxAttempts:1}")
  private int          feignMaxAttempts;

  @Value("${spring.application.name}")
  private String       appName;

  @Value("${feign.logger.level: BASIC}")
  private Logger.Level feignLoggingLevel;

  @Bean
  Logger.Level feignLoggerLevel() {
    return feignLoggingLevel;
  }

  @Bean
  Retryer feignRetryer() {
    return new Retryer.Default(100, SECONDS.toMillis(1), feignMaxAttempts);
  }

  @Bean
  public Contract feignContract() {
    return new Contract.Default();
  }

  @Bean
  FeignLoggerFactory feignLoggerFactory() {
    return new RetailFeignLoggerFactory();
  }

  @Bean
  @ConditionalOnMissingBean
  ErrorDecoder errorDecoder() {
    return new RetailFeignErrorDecoder();
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = true)
  public Feign.Builder feignBuilder() {

    SetterFactory setterFactory = (target, method) -> {
      String groupKey = appName + "." + target.name();
      LOGGER.info("Created group key for {} = {}", method.toString(), groupKey);
      String requestLine = method.getAnnotation(RequestLine.class).value();
      requestLine = StringUtils.replace(requestLine, "/", "-");
      requestLine = StringUtils.replace(requestLine, " ", "");
      requestLine = StringUtils.replace(requestLine, "{", "");
      requestLine = StringUtils.replace(requestLine, "}", "");
      String commandKey = groupKey + "." + requestLine;
      LOGGER.info("Created command key for {} = {}", method.toString(), commandKey);
      return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
          .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
    };

    return HystrixFeign.builder().setterFactory(setterFactory);
  }

}
