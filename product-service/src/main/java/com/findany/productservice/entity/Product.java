package com.findany.productservice.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String userId;
    @NotNull
    private String productName;
    @NotNull
    private int quantity;
    @NotNull
    private String uom;
    @NotNull
    private int price;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> imagesUrl;
    @NotNull
    private String category;
    @NotNull
    private String city;
    @NotNull
    @CreatedDate
    private Instant createdAt;
    @NotNull
    @LastModifiedDate
    private Instant lastModifiedAt;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> productDescription;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] imageBinary;
}
