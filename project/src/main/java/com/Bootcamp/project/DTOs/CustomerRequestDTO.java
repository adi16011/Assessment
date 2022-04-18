package com.Bootcamp.project.DTOs;


import com.Bootcamp.project.Enumerators.AddressLabel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequestDTO {


    @NotEmpty(message = "First name can't be Empty")
    private String firstName;


    @NotEmpty(message = "Last Name can't be Empty")
    private String lastName;

    private String middleName;



    @Column(unique = true)
    @Email(message = "Email not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;


    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8,message = "Size needs to be atleast 8")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",message = "Enter valid password")
    private String password;

    @NotEmpty(message = "Confirm Message field can't be Empty")
    @Size(min = 8)
    private String confirmPassword;






    @NotEmpty(message = "Contact can't be empty")
    @Size(min=0,max=10)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",message = "Enter a valid contact number")
    private String contact;







    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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



    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
