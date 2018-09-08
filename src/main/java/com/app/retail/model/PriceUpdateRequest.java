package com.app.retail.model;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceUpdateRequest {
  @NotBlank(message = "price field can't be empty")
  private String price;
}
