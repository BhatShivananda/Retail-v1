package com.app.retail.config.feign;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetailFeignErrorDecoder extends ErrorDecoder.Default {

  @Override
  public Exception decode(String methodKey, Response response) {
    Map<String, Collection<String>> requestHeaders = Collections.emptyMap();
    String requestUrl = "urlMissing";

    Map<String, Collection<String>> responseHeaders = response.headers();
    if (MapUtils.isEmpty(responseHeaders)) {
      responseHeaders = Collections.emptyMap();
    }
    if (response.request() != null) {
      requestHeaders = response.request().headers();
      if (MapUtils.isEmpty(requestHeaders)) {
        requestHeaders = Collections.emptyMap();
      }
      requestUrl = response.request().url();
    }

    Exception exception = super.decode(methodKey, response);

    String requestHeadersString = requestHeaders.toString();
    String responseHeadersString = responseHeaders.toString();

    LOGGER.error("Error received on feign call - {} - url - {} - requestHeaders - {} - responseHeaders - {}", methodKey,
        requestUrl, requestHeadersString, responseHeadersString, exception);

    return exception;
  }

}
