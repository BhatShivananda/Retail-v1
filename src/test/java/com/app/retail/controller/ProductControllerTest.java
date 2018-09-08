package com.app.retail.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.retail.entities.CurrentPrice;
import com.app.retail.entities.ItemPrice;
import com.app.retail.model.PriceUpdateRequest;
import com.app.retail.model.ProductResponse;
import com.app.retail.service.ProductService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductControllerTest {

  @Mock
  private ProductService    productService;

  @InjectMocks
  private ProductController productController;

  private MockMvc           mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
  }

  @Test
  public void testGetProductResponse() throws Exception {
    String tcin = "12345";
    Mockito.when(productService.getProductDetails(tcin)).thenReturn(getProductResponse(tcin));

    mockMvc.perform(get("/products/12345").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

  @Test
  public void testCreateItemPrice() throws Exception {
    ItemPrice itemPrice = ItemPrice.builder().tcin("12345").price("12").build();
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    Mockito.doNothing().when(productService).saveItemPrice(itemPrice);

    mockMvc
        .perform(post("/products").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
            gson.toJson(itemPrice)))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

  @Test
  public void testUpdateItemPrice() throws Exception {
    PriceUpdateRequest priceUpdateRequest = PriceUpdateRequest.builder().price("14").build();
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    Mockito.doNothing().when(productService).updateItemPrice(Mockito.anyObject());

    mockMvc.perform(
        put("/products/12345").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
            gson.toJson(priceUpdateRequest)))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

  @Test
  public void testDeleteItemPrice() throws Exception {
    Mockito.doNothing().when(productService).deleteItemPrice(Mockito.anyString());

    mockMvc
        .perform(delete("/products/12345").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }


  private ProductResponse getProductResponse(String tcin) {
    return ProductResponse.builder()
        .id(tcin)
        .name("candy")
        .currentPrice(CurrentPrice.builder().value("12").currencyCode("USD").build())
        .build();
  }
}
