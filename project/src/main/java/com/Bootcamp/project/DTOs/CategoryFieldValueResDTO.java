package com.Bootcamp.project.DTOs;

public class CategoryFieldValueResDTO {
    public long categoryId;
    public long fieldId;
    public String values;

    public CategoryFieldValueResDTO(long categoryId, long fieldId, String values) {
        this.categoryId = categoryId;
        this.fieldId = fieldId;
        this.values = values;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}