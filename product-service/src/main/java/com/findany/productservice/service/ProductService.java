package com.findany.productservice.service;

import com.findany.productservice.constants.AppConstants;
import com.findany.productservice.entity.Product;
import com.findany.productservice.model.ProductDto;
import com.findany.productservice.model.ProductFeed;
import com.findany.productservice.model.UserDetails;
import com.findany.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private UserDetails userDetails;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, ProductFeed> kafkaTemplate;

    public Product saveProduct(ProductDto productDto, MultipartFile image) throws IOException {
        Product product = Product.builder()
                .userId(userDetails.getUserId())
                .productName(productDto.getProductName())
                .quantity(productDto.getQuantity())
                .uom(productDto.getUom())
                .city(productDto.getCity())
                .category(productDto.getCategory())
                .imageBinary(image.getBytes())
                .productDescription(productDto.getProductDescription())
                .createdAt(Instant.now())
                .lastModifiedAt(Instant.now())
                .build();
        product = productRepository.save(product);
        ProductFeed productFeed = new ProductFeed(product.getId(), product.getCity(), product.getCreatedAt());
        kafkaTemplate.send(AppConstants.TOPIC_NAME, productFeed);
        log.info("New product added successfully with product id {}", product.getId());
        return product;
    }

    public Product updateProduct(ProductDto productDto) {
        Optional<Product> productOptional = getProduct(productDto.getId());
        if (!productOptional.isPresent()) {
            log.error("Error occurred: Update failed due to product not found for product id {}", productDto.getId());
            throw new RuntimeException("Product not found");
        }
        Product product = productOptional.get();
        product.setProductName(productDto.getProductName());
        product.setQuantity(productDto.getQuantity());
        product.setUom(productDto.getUom());
        product.setCity(productDto.getCity());
        product.setCategory(productDto.getCategory());
        product.setProductDescription(productDto.getProductDescription());
        product.setLastModifiedAt(Instant.now());
        product = productRepository.save(product);
        ProductFeed productFeed = new ProductFeed(product.getId(), product.getCity(), product.getLastModifiedAt());
        kafkaTemplate.send(AppConstants.TOPIC_NAME, productFeed);
        log.info("Update for the product id {} was successful", product.getId());
        return product;
    }

    public Optional<Product> getProduct(int productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findByUserId(userDetails.getUserId());
    }

    public List<Product> findByIdIn(List<Integer> ids) {
        return productRepository.findByIdInOrderByCreatedAtDesc(ids);
    }

}
