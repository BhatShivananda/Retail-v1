package com.app.retail.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.app.retail.config.feign.RedskyFeignClient;
import com.app.retail.dao.RetailDaoImpl;
import com.app.retail.entities.Item;
import com.app.retail.entities.ItemPrice;
import com.app.retail.entities.Product;
import com.app.retail.entities.ProductDescription;
import com.app.retail.error.ProductNotFoundException;
import com.app.retail.model.ProductResponse;
import com.app.retail.model.RedskyResponse;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  @Mock
  private RedskyFeignClient redskyFeignClientMock;

  @Mock
  private RetailDaoImpl     retailDaoImplMock;

  private ProductService    productService;

  @Before
  public void setup() {
    productService = new ProductService(redskyFeignClientMock, retailDaoImplMock);
  }

  @Test
  public void testGetProductDetails() {
    Mockito.when(redskyFeignClientMock.getProductDetails(Mockito.anyString())).thenReturn(getRedskyReponse());
    Mockito.when(retailDaoImplMock.getItemPrice(Mockito.anyString()))
        .thenReturn(ItemPrice.builder().price("12").build());

    ProductResponse productResponse = productService.getProductDetails("12345");
    Mockito.verify(redskyFeignClientMock, Mockito.times(1)).getProductDetails(Mockito.anyString());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).getItemPrice(Mockito.anyString());
    Assert.assertNotNull(productResponse);
    Assert.assertEquals(productResponse.getName(), "candy");
    Assert.assertEquals(productResponse.getCurrentPrice().getValue(), "12");
  }

  @Test(expected = ProductNotFoundException.class)
  public void testGetProductDetailsWithException() {
    Mockito.when(redskyFeignClientMock.getProductDetails(Mockito.anyString())).thenReturn(new RedskyResponse());
    Mockito.when(retailDaoImplMock.getItemPrice(Mockito.anyString()))
        .thenReturn(ItemPrice.builder().price("12").build());

    productService.getProductDetails("12345");
    Mockito.verify(redskyFeignClientMock, Mockito.times(1)).getProductDetails(Mockito.anyString());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).getItemPrice(Mockito.anyString());
  }

  @Test(expected = ProductNotFoundException.class)
  public void testGetProductDetailsNullPriceWithException() {
    Mockito.when(redskyFeignClientMock.getProductDetails(Mockito.anyString())).thenReturn(getRedskyReponse());
    Mockito.when(retailDaoImplMock.getItemPrice(Mockito.anyString())).thenReturn(null);

    productService.getProductDetails("12345");
    Mockito.verify(redskyFeignClientMock, Mockito.times(1)).getProductDetails(Mockito.anyString());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).getItemPrice(Mockito.anyString());
  }

  @Test(expected = ProductNotFoundException.class)
  public void testGetProductDetailsNoPriceWithException() {
    Mockito.when(redskyFeignClientMock.getProductDetails(Mockito.anyString())).thenReturn(getRedskyReponse());
    Mockito.when(retailDaoImplMock.getItemPrice(Mockito.anyString())).thenReturn(new ItemPrice());

    productService.getProductDetails("12345");
    Mockito.verify(redskyFeignClientMock, Mockito.times(1)).getProductDetails(Mockito.anyString());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).getItemPrice(Mockito.anyString());
  }

  @Test
  public void testSaveItemPrice() {
    Mockito.doNothing().when(retailDaoImplMock).saveItemPrice(Mockito.any(ItemPrice.class));
    productService.saveItemPrice(ItemPrice.builder().tcin("12345").price("12").build());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).saveItemPrice(Mockito.any(ItemPrice.class));
  }

  @Test
  public void testUpdateItemPrice() {
    Mockito.doNothing().when(retailDaoImplMock).saveItemPrice(Mockito.any(ItemPrice.class));
    productService.updateItemPrice(ItemPrice.builder().tcin("12345").price("12").build());
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).updateItemPrice(Mockito.any(ItemPrice.class));
  }

  @Test
  public void testDeleteItemPrice() {
    Mockito.doNothing().when(retailDaoImplMock).deleteItem(Mockito.anyString());
    productService.deleteItemPrice("12345");
    Mockito.verify(retailDaoImplMock, Mockito.times(1)).deleteItem(Mockito.anyString());
  }

  private RedskyResponse getRedskyReponse() {
    return RedskyResponse.builder()
        .product(Product.builder()
            .item(Item.builder().productDescription(ProductDescription.builder().title("candy").build()).build())
            .build())
        .build();
  }
}
