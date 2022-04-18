package com.Bootcamp.project.DTOs;

public class GetCustomersResponseDTO {


    private long Id;

    private String fullName;

    private String email;

    private boolean Is_Active;

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

    public GetCustomersResponseDTO(long id, String fullName, String email, boolean is_Active) {
        Id = id;
        this.fullName = fullName;
        this.email = email;
        Is_Active = is_Active;
    }

    public GetCustomersResponseDTO() {
    }
}
