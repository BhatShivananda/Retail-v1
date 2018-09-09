package com.app.retail.auth;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {

  private MockHttpServletRequest  httpServletRequestMock  = new MockHttpServletRequest();

  private MockHttpServletResponse httpServletResponseMock = new MockHttpServletResponse();

  private MockFilterChain         filterChainMock         = new MockFilterChain();

  private AuthFilter              authFilter;

  private AuthFilterProps         authFilterProps         =
      new AuthFilterProps("Test-Service", "Bearer access_token", "/swagger-ui.html",false);

  @Before
  public void setup() {
    authFilter = new AuthFilter(authFilterProps);
  }

  @Test
  public void testShouldFilterSkipsSwagger() throws Exception {
    httpServletRequestMock.setServletPath("/swagger-ui.html");
    authFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);
  }

  @Test
  public void testWithInvalidAccessToken() throws Exception {
    httpServletRequestMock.setServletPath("/products");
    httpServletRequestMock.addHeader("Authorization", "12132434ssfsfab");
    authFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);
    assertEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponseMock.getStatus());
  }

  @Test
  public void testWithValidAccessToken() throws Exception {
    httpServletRequestMock.setServletPath("/products");
    httpServletRequestMock.addHeader("Authorization", "Bearer access_token");
    authFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);
    assertNotEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponseMock.getStatus());
  }

  @Test
  public void testValidHeaderRequestWithProceedOnUnauthorized() throws Exception {
    authFilterProps =
            new AuthFilterProps("Test-Service", "Bearer access_token", "/swagger-ui.html",true);
    authFilter = new AuthFilter(authFilterProps);
    authFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);
    assertNotEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponseMock.getStatus());
  }

}