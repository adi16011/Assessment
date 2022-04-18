package com.Bootcamp.project.DTOs;

import javax.validation.constraints.NotEmpty;

public class ProductRequestDTO {


    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    private String description;

    private boolean canBeCancelled;

    private boolean canBeReturned;





    private Long categoryId;



    public boolean isCanBeCancelled() {
        return canBeCancelled;
    }

    public void setCanBeCancelled(boolean canBeCancelled) {
        this.canBeCancelled = canBeCancelled;
    }

    public boolean isCanBeReturned() {
        return canBeReturned;
    }

    public void setCanBeReturned(boolean canBeReturned) {
        this.canBeReturned = canBeReturned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
