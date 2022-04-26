package com.mitrais.cdcpos.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntityAudit {

//    @CreatedBy
//    @Column(name = "created_by")
//    private U createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    @LastModifiedBy
//    @Column(name = "modified_by")
//    private U modifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

//    @Column(name = "deleted_by")
//    private U deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
