package com.Bootcamp.project.DTOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

public class CategoryMetadataFieldValueDTO {

    @Positive(message = "Enter Category Id")
    private long categoryId;

    @Positive(message = "Enter MetaData Field Id")
    private long fieldId;

    @NotEmpty(message = "Enter MetaData Field Value")
    private Set<String> values;



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

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }


}