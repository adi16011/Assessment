package com.Bootcamp.project.Entities;

import com.Bootcamp.project.Auditing.Auditable;
import com.Bootcamp.project.Enumerators.AddressLabel;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Entity
@EnableJpaAuditing
public class Address extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String city;

    private String state;

    private String country;

    private String addressLine;

    private Long zipCode;

    @Enumerated(EnumType.STRING)
    private AddressLabel label;

    @JoinColumn(name = "user_id",unique = true)
    @OneToOne(cascade = CascadeType.MERGE)
    private UserEntity userId;



    public UserEntity getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }
}
