package com.jsw.mes.mdm.entity;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MesWorkCenterMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"workCenterName"})})
public class WorkCenterMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workCenterId")
    @Range(min=0, max=99999)
    private int workCenterId;

    @Column(name = "workCenterName",unique = true)
    @Length(min=0,max = 15)
    private String workCenterName;

    @Column(name = "workCenterDesc")
    @Length(min=0,max = 100)
    private String workCenterDescription;


    @Column(name = "isActive")
    @Length(max=1)
    private String isActive;


}
