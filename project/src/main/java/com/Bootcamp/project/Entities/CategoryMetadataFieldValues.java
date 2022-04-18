package com.Bootcamp.project.Entities;


import com.Bootcamp.project.Auditing.Auditable;

import javax.persistence.*;

@Entity
public class CategoryMetadataFieldValues {

    @EmbeddedId
    private CategoryMetadataComposite id;


    @ManyToOne
    @MapsId("category")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("categoryMetadataField")
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    private String value;



    public CategoryMetadataFieldValues() {
    }

    public CategoryMetadataComposite getId() {
        return id;
    }

    public void setId(CategoryMetadataComposite id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
