package com.Bootcamp.project.Entities;

import com.Bootcamp.project.Auditing.Auditable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Entity
@EnableJpaAuditing
public class Customer extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @JoinColumn(name = "id", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private UserEntity userId;

    private String contact;

    public Customer() {
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public Customer(Long cid, String contact) {
        this.cid = cid;
        this.contact = contact;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
