package com.jsw.mes.mdm.entity;


import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="MesDepartmentMst",schema="user_service",uniqueConstraints = {@UniqueConstraint(columnNames = {"departmentName"})})
public class DepartmentMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "departmentId")
    @Range(min=0,max=99999)
    private int departmentId;

    @Column(name = "departmentName",unique = true)
    @Length(min=0,max=15)
    private String departmentName;

    @Column(name = "isActive")
    @Length(max=1)
    private String isActive;


}
