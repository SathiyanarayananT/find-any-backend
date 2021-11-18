package com.findany.productfeedservice.service;

import com.findany.productfeedservice.constants.AppConstants;
import com.findany.productfeedservice.feignclient.ProductServiceClient;
import com.findany.productfeedservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductServiceClient productServiceClient;

    public List<Product> findProductsByIdIn(String token, List<Integer> ids) {
        try {
            return productServiceClient.findProductByIdIn(AppConstants.BEARER + token, ids);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
