package com.findany.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;
    private String productName;
    private int quantity;
    private String uom;
    private int price;
    private String category;
    private String city;
    private Map<String, String> productDescription;

}
