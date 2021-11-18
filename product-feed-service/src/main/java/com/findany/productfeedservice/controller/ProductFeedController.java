package com.findany.productfeedservice.controller;

import com.findany.productfeedservice.constants.AppConstants;
import com.findany.productfeedservice.model.Product;
import com.findany.productfeedservice.service.ProductFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product-feed")
public class ProductFeedController {

    @Autowired
    private ProductFeedService productFeedService;

    @GetMapping("/{city}")
    public Slice<Product> getProductFeedBasedOnLocation(@PathVariable String city,
                                                        @RequestParam("page") int page,
                                                        @RequestParam(name = "size", required = false) Optional<Integer> sizeOptional) {
        int size = sizeOptional.isPresent() ? sizeOptional.get() : AppConstants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastModifiedAt").descending());
        return productFeedService.getProductFeedBasedOnLocation(city, pageable);
    }

}
