package com.findany.productfeedservice.feignclient;

import com.findany.productfeedservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceClient {

    @PostMapping("/find-any/product/in")
    List<Product> findProductByIdIn(@RequestHeader("Authorization") String token, @RequestBody List<Integer> ids);

}

