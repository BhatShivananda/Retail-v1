package com.app.retail.error;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

@Component
public class EdgeErrorAttributes extends DefaultErrorAttributes {

  private static final List<String> EXPOSABLE_FIELDS = Arrays.asList("status", "error", "message");

  @Override
  public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
    errorAttributes.keySet().removeIf(key -> !EXPOSABLE_FIELDS.contains(key));
    return errorAttributes;
  }
}
