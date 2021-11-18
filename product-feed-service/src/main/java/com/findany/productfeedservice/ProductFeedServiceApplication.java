package com.findany.productfeedservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductFeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductFeedServiceApplication.class, args);
	}

}
