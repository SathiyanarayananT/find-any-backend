package com.findany.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String addressLine;
    private String city;
    private String state;
    private String pinOrZipCode;

}
