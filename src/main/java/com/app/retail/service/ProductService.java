package com.app.retail.service;


import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.retail.config.feign.RedskyFeignClient;
import com.app.retail.dao.RetailDaoImpl;
import com.app.retail.entities.*;
import com.app.retail.error.ProductNotFoundException;
import com.app.retail.model.ProductResponse;
import com.app.retail.model.RedskyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

  private final RedskyFeignClient redskyFeignClient;

  private final RetailDaoImpl     retailDaoImpl;

  public ProductResponse getProductDetails(String tcin) {

    RedskyResponse redskyResponse = redskyFeignClient.getProductDetails(tcin);
    ItemPrice itemPrice = retailDaoImpl.getItemPrice(tcin);

    Optional<String> productTitle = Optional.of(redskyResponse)
        .map(RedskyResponse::getProduct)
        .map(Product::getItem)
        .map(Item::getProductDescription)
        .map(ProductDescription::getTitle);

    if (productTitle.isPresent() && null != itemPrice && StringUtils.isNotBlank(itemPrice.getPrice())) {
      return ProductResponse.builder()
          .id(tcin)
          .name(productTitle.get())
          .currentPrice(CurrentPrice.builder().value(itemPrice.getPrice()).currencyCode("USD").build())
          .build();

    }
    throw new ProductNotFoundException("product not found");
  }

  public void saveItemPrice(ItemPrice itemPrice) {
    retailDaoImpl.saveItemPrice(itemPrice);
  }

  public void updateItemPrice(ItemPrice itemPrice) {
    retailDaoImpl.updateItemPrice(itemPrice);
  }

  public void deleteItemPrice(String tcin) {
    retailDaoImpl.deleteItem(tcin);
  }

}
