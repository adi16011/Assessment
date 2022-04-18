package com.Bootcamp.project.Entities;


import com.Bootcamp.project.Auditing.Auditable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.util.Set;

@Entity
@EnableJpaAuditing
public class Seller extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String gst;

    private String companyContact;

    private String companyName;

    @JoinColumn(name = "id", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private UserEntity userId;

    @OneToMany(mappedBy = "seller")
    private Set<Product> products;



    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public Seller(Long sid, String gst, String companyContact, String companyName, UserEntity userId) {
        this.sid = sid;
        this.gst = gst;
        this.companyContact = companyContact;
        this.companyName = companyName;
        this.userId = userId;
    }

    public Seller() {
    }
}
