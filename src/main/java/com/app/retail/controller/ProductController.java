package com.app.retail.controller;


import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.app.retail.entities.ItemPrice;
import com.app.retail.model.PriceUpdateRequest;
import com.app.retail.model.ProductResponse;
import com.app.retail.service.ProductService;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/products")
public class ProductController {

  public static final String   BASE_METRIC_URI = "/retail/v1/products";

  private final ProductService productService;

  @GetMapping(value = {"/{tcin}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed(name = BASE_METRIC_URI + "/{tcin}.GET", absolute = true)
  @ExceptionMetered(name = BASE_METRIC_URI + "/{tcin}.GET." + ExceptionMetered.DEFAULT_NAME_SUFFIX, absolute = true)
  @Metered(name = BASE_METRIC_URI + "/{tcin}.GET")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ProductResponse get(@PathVariable @NotBlank String tcin) {
    return productService.getProductDetails(tcin);
  }

  @PostMapping
  @Timed(name = BASE_METRIC_URI + "/.POST", absolute = true)
  @ExceptionMetered(name = BASE_METRIC_URI + "/.POST" + ExceptionMetered.DEFAULT_NAME_SUFFIX, absolute = true)
  @Metered(name = BASE_METRIC_URI + "/.POST")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@Valid @RequestBody ItemPrice itemPrice) {
    productService.saveItemPrice(itemPrice);
  }

  @PutMapping(value = {"/{tcin}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed(name = BASE_METRIC_URI + "/{tcin}.PUT", absolute = true)
  @ExceptionMetered(name = BASE_METRIC_URI + "/{tcin}.PUT" + ExceptionMetered.DEFAULT_NAME_SUFFIX, absolute = true)
  @Metered(name = BASE_METRIC_URI + "/{tcin}.PUT")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable @NotBlank String tcin, @Valid @RequestBody PriceUpdateRequest priceUpdateRequest) {
    productService.updateItemPrice(ItemPrice.builder().tcin(tcin).price(priceUpdateRequest.getPrice()).build());
  }

  @DeleteMapping(value = {"/{tcin}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed(name = BASE_METRIC_URI + "/{tcin}.DELETE", absolute = true)
  @ExceptionMetered(name = BASE_METRIC_URI + "/{tcin}.DELETE." + ExceptionMetered.DEFAULT_NAME_SUFFIX, absolute = true)
  @Metered(name = BASE_METRIC_URI + "/{tcin}.DELETE")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable @NotBlank String tcin) {
    productService.deleteItemPrice(tcin);
  }

}
