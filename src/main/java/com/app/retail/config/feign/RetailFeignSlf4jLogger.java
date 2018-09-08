package com.app.retail.config.feign;

import feign.Logger;

public class RetailFeignSlf4jLogger extends Logger {
  private final org.slf4j.Logger logger;

  public RetailFeignSlf4jLogger(org.slf4j.Logger logger) {
    this.logger = logger;
  }

  @Override
  protected void log(String configKey, String format, Object... args) {
    String formattedLogLine = String.format(methodTag(configKey) + format, args);
    logger.info(formattedLogLine);
  }


}
