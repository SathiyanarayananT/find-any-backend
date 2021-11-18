package com.findany.authservice.entity;

import com.findany.authservice.model.Provider;
import com.findany.authservice.model.RoleType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "user_secrets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UserSecret {

    @Id
    private int id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private boolean active;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Type(type = "jsonb")
    private List<RoleType> roles;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;

}
