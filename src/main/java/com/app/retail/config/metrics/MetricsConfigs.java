package com.app.retail.config.metrics;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;


@Configuration
@EnableMetrics
@EnableMBeanExport(defaultDomain = "Retail-v1")
public class MetricsConfigs extends MetricsConfigurerAdapter {

  @Value("${metrics.console.enabled:true}")
  private boolean consoleEnabled;

  @Override
  public void configureReporters(MetricRegistry metricRegistry) {
    if (consoleEnabled) {
      registerReporter(getConsoleReporter(metricRegistry));
    }
    registerReporter(getJMXReporter(metricRegistry));
  }

  private ConsoleReporter getConsoleReporter(MetricRegistry metricRegistry) {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
    reporter.start(1, TimeUnit.MINUTES);
    return reporter;
  }

  private JmxReporter getJMXReporter(MetricRegistry metricRegistry) {
    JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
    reporter.start();
    return reporter;
  }

}
