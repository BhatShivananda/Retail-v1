package com.app.retail.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends ApplicationException {

  public ProductNotFoundException(String message) {
    super(message);
  }

}
