package com.findany.productfeedservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Table(name = "product_feed")
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeed {

    @Id
    @NotNull
    private int productId;
    @NotNull
    private String city;
    @NotNull
    private Instant lastModifiedAt;
    
}
