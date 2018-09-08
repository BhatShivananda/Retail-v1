package com.app.retail.entities;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_price")
public class ItemPrice {

  @PartitionKey
  @Column(name = "tcin")
  @NotBlank(message = "tcin field can't be empty")
  private String tcin;

  @Column(name = "price")
  @NotBlank(message = "price field can't be empty")
  private String price;

  @Column(name = "created_at")
  private Date   createdAt;

  @Column(name = "updated_at")
  private Date   updatedAt;
}
