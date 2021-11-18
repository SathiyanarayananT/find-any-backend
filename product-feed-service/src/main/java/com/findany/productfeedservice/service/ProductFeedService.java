package com.findany.productfeedservice.service;

import com.findany.productfeedservice.constants.AppConstants;
import com.findany.productfeedservice.entity.ProductFeed;
import com.findany.productfeedservice.model.Product;
import com.findany.productfeedservice.repository.ProductFeedRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ProductFeedService {

    @Autowired
    private ProductFeedRepository productFeedRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    public Slice<Product> getProductFeedBasedOnLocation(String city, Pageable pageable) {
        Slice<ProductFeed> productFeedPage = productFeedRepository.findByCity(city, pageable);
        if (productFeedPage.isEmpty()) {
            throw new RuntimeException("Feed not found");
        }
        List<Integer> ids = productFeedPage.stream().map(feedPage -> feedPage.getProductId()).collect(Collectors.toList());
        String bearerToken = AppConstants.BEARER + authService.getAccessToken();
        List<Product> products = productService.findProductsByIdIn(bearerToken, ids);
        return new SliceImpl<>(products, pageable, productFeedPage.hasNext());
    }

}
