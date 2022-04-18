package com.Bootcamp.project.DTOs;

import com.Bootcamp.project.Enumerators.AddressLabel;

public class CustomerResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private boolean is_Active;

    private String contact;







    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(Long id, String firstName, String lastName, boolean is_Active, String contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.is_Active = is_Active;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isIs_Active() {
        return is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        this.is_Active = is_Active;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
