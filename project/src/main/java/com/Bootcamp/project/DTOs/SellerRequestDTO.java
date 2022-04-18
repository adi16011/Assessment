package com.Bootcamp.project.DTOs;

import com.Bootcamp.project.Enumerators.AddressLabel;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SellerRequestDTO {

    @NotEmpty(message = "First name can't be Empty")
    private String firstName;

    private String middleName;



    @NotEmpty(message = "Last Name can't be Empty")
    private String lastName;


    @Column(unique = true)
    @Email(message = "Email not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;


    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8,message = "Size needs to be atleast 8")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",message = "Enter valid password")
    private String password;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty(message = "Confirm Password field cannot be empty")
    private String confirmPassword;


    @NotEmpty(message = "GST can't be empty")
    @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}",message = "Enter valid GST")
    private String gst;

    @NotEmpty(message = "Contact can't be empty")
    @Size(min=0,max=15)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",message = "Enter a valid contact number")
    private String companyContact;


    @NotEmpty(message = "Company Name can't be empty")
    private String companyName;


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

    private  String city;

    private String state;

    private String country;

    private String addressLine;

    private Long zipCode;

    private AddressLabel label;

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public AddressLabel getLabel() {
        return label;
    }

    public void setLabel(AddressLabel label) {
        this.label = label;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public SellerRequestDTO(String firstName, String lastName, String email, String password, String gst, String companyContact, String companyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gst = gst;
        this.companyContact = companyContact;
        this.companyName = companyName;
    }

    public SellerRequestDTO() {


    }
}
