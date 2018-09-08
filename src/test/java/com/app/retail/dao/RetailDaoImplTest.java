package com.app.retail.dao;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.app.retail.entities.ItemPrice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CassandraTestConfig.class})
@TestExecutionListeners(listeners = {CassandraUnitDependencyInjectionTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class})
@EmbeddedCassandra(configuration = "cassandra.yml")
@CassandraDataSet(value = {"cqls/createtables.cql", "cqls/insertItemdetails.cql"})
public class RetailDaoImplTest {

  @Autowired
  RetailDaoImpl retailDaoImpl;

  @Test
  public void testGetItemPrice() {
    ItemPrice itemPrice = retailDaoImpl.getItemPrice("13860428");
    Assert.assertNotNull(itemPrice);
    Assert.assertEquals(itemPrice.getPrice(), "123.23");
  }

  @Test
  public void testSaveItemPrice() {
    retailDaoImpl.saveItemPrice(ItemPrice.builder().tcin("12345").price("12").build());
    ItemPrice itemPrice = retailDaoImpl.getItemPrice("12345");
    Assert.assertNotNull(itemPrice);
    Assert.assertEquals(itemPrice.getPrice(), "12");
  }

  @Test
  public void testUpdateItemPrice() {
    retailDaoImpl.updateItemPrice(ItemPrice.builder().tcin("12345").price("10").build());
    ItemPrice itemPrice = retailDaoImpl.getItemPrice("12345");
    Assert.assertNotNull(itemPrice);
    Assert.assertEquals(itemPrice.getPrice(), "10");
  }

  @Test
  public void testDeleteItem() {
    retailDaoImpl.saveItemPrice(ItemPrice.builder().tcin("12345").price("10").build());
    retailDaoImpl.deleteItem("12345");
    ItemPrice itemPrice = retailDaoImpl.getItemPrice("12345");
    Assert.assertNull(itemPrice);
  }
}
