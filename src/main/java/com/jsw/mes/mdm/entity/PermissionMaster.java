package com.jsw.mes.mdm.entity;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MesPermissionMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"permissionName"})})
public class PermissionMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permissionId")
    @Range(min=0,max=99999)
    private int permissionId;

    @Column(name = "permissionName")
    @Length(min=0,max = 50)
    private String permissionName;

    @Column(name = "isActive")
    private String isActive;


}
