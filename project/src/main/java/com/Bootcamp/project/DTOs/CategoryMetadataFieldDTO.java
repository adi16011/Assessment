package com.Bootcamp.project.DTOs;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class CategoryMetadataFieldDTO {
    @Column(unique=true)
    @NotBlank(message = "Enter Your Category Metadata Field")
    private String  name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
