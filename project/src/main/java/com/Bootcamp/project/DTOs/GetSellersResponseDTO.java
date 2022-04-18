package com.Bootcamp.project.DTOs;

public class GetSellersResponseDTO {

    private long Id;

    private String fullName;

    private String email;

    private boolean Is_Active;

    private String companyName;

    private  String companyContact;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_Active() {
        return Is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        Is_Active = is_Active;
    }

    public GetSellersResponseDTO(long id, String fullName, String email, boolean is_Active) {
        Id = id;
        this.fullName = fullName;
        this.email = email;
        Is_Active = is_Active;
    }

    public GetSellersResponseDTO() {
    }
}
