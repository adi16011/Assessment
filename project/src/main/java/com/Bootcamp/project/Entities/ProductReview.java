package com.Bootcamp.project.Entities;

import com.Bootcamp.project.Auditing.Auditable;

import javax.persistence.*;

@Entity
public class ProductReview extends Auditable<String> {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToOne
    @JoinColumn(name = "customer_user_id")
    @MapsId
    private Customer customerId;

    private String review;
    private Long rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;



    public ProductReview() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

}
