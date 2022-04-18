package com.Bootcamp.project.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class CategoryMetadataComposite implements Serializable {

    @Column(name = "category_id")
    private Long category;

    @Column(name = "category_metadata_field_id")
    private Long categoryMetadataField;

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(Long categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryMetadataComposite that = (CategoryMetadataComposite) o;
        return Objects.equals(category, that.category) && Objects.equals(categoryMetadataField, that.categoryMetadataField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, categoryMetadataField);
    }
}
