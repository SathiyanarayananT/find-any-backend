package com.findany.productfeedservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int Id;
    private String userId;
    private String productName;
    private int quantity;
    private String uom;
    private int price;
    private List<String> imagesUrl;
    private String category;
    private String city;
    private Instant createdAt;
    private Instant lastModifiedAt;
    private Map<String, String> productDescription;

}
