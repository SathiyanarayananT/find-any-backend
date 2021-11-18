package com.findany.ratingreviewservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Table("reviews")
public class Review {

    @Id
    private int id;
    @NotNull
    private int productId;
    @NotNull
    private String userId;
    @NotNull
    private String comment;

}
