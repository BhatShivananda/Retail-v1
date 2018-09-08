package com.app.retail.config.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.app.retail.model.RedskyResponse;

import feign.Param;
import feign.RequestLine;


@FeignClient(name = "redsky", url = "${feign.redsky.baseURL}", decode404 = true)
public interface RedskyFeignClient {

  @RequestLine("GET /v2/pdp/tcin/{tcin}")
  RedskyResponse getProductDetails(@Param("tcin") String tcin);

}
