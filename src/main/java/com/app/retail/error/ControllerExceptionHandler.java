package com.app.retail.error;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Jackson converter failed")
  @ExceptionHandler(HttpMessageConversionException.class)
  public void jacksonConverterExceptions(Exception e) {
    LOGGER.error("Jackson converter failed : ", e);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "product not found")
  @ExceptionHandler(ProductNotFoundException.class)
  public void productNotFoundException(Exception e) {
    LOGGER.error("product not found : ", e);
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void methodArgumentException(HttpServletResponse response, MethodArgumentNotValidException e)
      throws IOException {
    BindingResult result = e.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    List<String> errorList =
        fieldErrors.stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
    LOGGER.error(errorList.get(0), e);
    response.sendError(HttpStatus.BAD_REQUEST.value(), errorList.get(0));
  }

  @ExceptionHandler(Exception.class)
  public void handleException(HttpServletResponse response, Exception e) throws IOException {
    LOGGER.error("Exception while processing the request: ", e);
    response.sendError(getHttpStatus(e).value(), e.getMessage());
  }

  private HttpStatus getHttpStatus(Exception e) {
    ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
    return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
