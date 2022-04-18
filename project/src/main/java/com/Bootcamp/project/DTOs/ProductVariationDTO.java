package com.Bootcamp.project.DTOs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class ProductVariationDTO {



    private Long productId;



    private Map<String,String> metadata;


    @Min(value = 0,message = "Enter valid Quantity")

    private Long quantity;


    @Min(value = 0,message = "Enter Valid Price")

    private Long price;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
