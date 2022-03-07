package com.findany.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findany.productservice.entity.Product;
import com.findany.productservice.model.ProductDto;
import com.findany.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Logger log = LoggerFactory.getLogger(ProductController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int productId) {
        Optional<Product> productOptional = productService.getProduct(productId);
        if (!productOptional.isPresent()) {
            log.error("Error occurred: Product not found for product id {}", productId);
            throw new RuntimeException("Product not found");
        }
        return productOptional.get();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Product addProduct(@RequestPart("product") String product, @RequestPart("image") MultipartFile image) throws IOException {
        ProductDto productDto = mapper.readValue(product, ProductDto.class);
        return productService.saveProduct(productDto, image);
    }

    @PutMapping
    public Product updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping("/in")
    public List<Product> findProductByIdIn(@RequestBody List<Integer> ids) {
        return productService.findByIdIn(ids);
    }

}
