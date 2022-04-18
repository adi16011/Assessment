package com.Bootcamp.project.DTOs;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class CategoryDTO {
    @Column(unique = true)
    @NotBlank(message = "Enter Category Name")
    private String name;
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}