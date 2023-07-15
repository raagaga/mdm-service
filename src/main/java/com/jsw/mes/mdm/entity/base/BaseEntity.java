package com.jsw.mes.mdm.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
//@Builder
public abstract class BaseEntity implements Serializable {

    @Column(name = "isActive")
    @Length(min=0,max = 1)
    private String isActive;

    @Column(name = "createdBy")
    @Length(min=0,max = 30)
    private String createdBy;

    @Column(name = "createdDt")
    private Instant createdDate;

    @Column(name = "modifiedBy")
    @Length(min=0,max = 30)
    private String modifiedBy;

    @Column(name = "modifiedDt")
    private Instant modifiedDate;

}
