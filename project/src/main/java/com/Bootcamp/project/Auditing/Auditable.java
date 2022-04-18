package com.Bootcamp.project.Auditing;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;

import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    protected U createdBy;

    @CreatedDate
//    @Temporal(TemporalType.DATE)
    protected  U dateCreated;

    @LastModifiedDate
//    @Temporal(TemporalType.DATE)
    protected U lastUpdated;

    @LastModifiedBy
    protected  U updatedBy;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public U getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(U dateCreated) {
        this.dateCreated = dateCreated;
    }

    public U getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(U lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public U getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(U updatedBy) {
        this.updatedBy = updatedBy;
    }
}
