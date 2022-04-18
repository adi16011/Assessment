package com.Bootcamp.project.DTOs;

import com.Bootcamp.project.Enumerators.AddressLabel;
import org.springframework.security.core.parameters.P;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddressChangeRequestDTO {


    @Pattern(regexp = "^[a-zA-Z',.\\s-]{1,25}$",message = "Please Enter a valid city name")
    private  String city;

    @Pattern(regexp = "^[a-zA-Z',.\\s-]{1,25}$",message = "Please enter a valid state name")
    private String state;

    @Pattern(regexp = "[a-zA-Z]{2,}",message = "Enter a valid country name")
    private String country;

    @Size(min = 2,message = "Address size must be greater than 2")
    private String addressLine;


    private Long zipCode;


    private AddressLabel label;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public AddressLabel getLabel() {
        return label;
    }

    public void setLabel(AddressLabel label) {
        this.label = label;
    }

    public AddressChangeRequestDTO() {
    }
}
