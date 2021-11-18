package com.findany.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeed {

    private int productId;
    private String city;
    private Instant lastModifiedAt;

}
