package com.app.retail.dao;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface RetailAccessor {

  @Query("UPDATE item_price SET price = :price, updated_at = dateof(now()) WHERE tcin = :tcin")
  void updateItemPrice(@Param("tcin") String tcin, @Param("price") String price);

  @Query("DELETE FROM item_price WHERE tcin = :tcin IF EXISTS")
  void deleteItemRecord(@Param("tcin") String tcin);

}
