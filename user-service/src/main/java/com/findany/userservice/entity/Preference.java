package com.findany.userservice.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "preferences")
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Preference {

    @Id
    @Column(name = "user_id")
    private int userId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> categories;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> serviceLocations;
    @NotNull
    private boolean selfDeliveryWithinCity;
    @NotNull
    private boolean deliveryServiceRequired;

}
