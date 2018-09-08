package com.app.retail.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.retail.entities.ItemPrice;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.mapping.MappingManager;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RetailDaoImpl extends BaseAccessorDao<ItemPrice, RetailAccessor> {

  @Autowired
  public RetailDaoImpl(MappingManager mappingManager) {
    super(mappingManager);
  }

  @Timed
  @ExceptionMetered
  public ItemPrice getItemPrice(String tcin) {
    LOGGER.info("finding by tcin " + tcin);
    return crud.get(tcin);
  }

  @Timed
  @ExceptionMetered
  public void saveItemPrice(ItemPrice itemPrice) {
    LOGGER.info("adding item price {} for tcin {}", itemPrice.getPrice(), itemPrice.getTcin());
    Date date = new Date();
    itemPrice.setCreatedAt(date);
    crud.save(itemPrice);
  }

  @Timed
  @ExceptionMetered
  public void updateItemPrice(ItemPrice itemPrice) {
    LOGGER.info("updating item price {} for tcin {}", itemPrice.getPrice(), itemPrice.getTcin());
    accessor.updateItemPrice(itemPrice.getTcin(), itemPrice.getPrice());
  }

  @Timed
  @ExceptionMetered
  public void deleteItem(String tcin) {
    LOGGER.info("deleting item by tcin " + tcin);
    accessor.deleteItemRecord(tcin);
  }
}
