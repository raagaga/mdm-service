package com.jsw.mes.mdm.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 4286140203575460473L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDt = Instant.now();;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private long createdBy = Long.parseLong(String.valueOf(0L));

    @LastModifiedDate
    @Column(nullable = false)
    private Instant modifiedDt= Instant.now();;

    @LastModifiedBy
    @Column(nullable = false)
    private Long modifiedBy = Long.valueOf(String.valueOf(0L));

}
