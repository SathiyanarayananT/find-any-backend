package com.findany.ratingreviewservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Table("likes")
public class Like {

    @Id
    private int id;
    @NotNull
    private int productId;
    @NotNull
    private String userId;

}
