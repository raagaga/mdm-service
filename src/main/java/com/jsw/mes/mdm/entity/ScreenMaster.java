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
@Table(name = "MesScreenMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"screenName"})})
public class ScreenMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "screenId")
    @Range(min=0,max=99999)
    private int screenId;

    @Column(name = "screenName")
    @Length(min=0,max = 100)
    private String screenName;

    @Column(name = "moduleName")
    @Length(min=0,max = 50)
    private String moduleName;

    @Column(name = "parentId")
    @Range(min=0,max=99999)
    private int parentId;

    @Column(name = "screenType")
    @Length(min=0,max = 50)
    private String screenType;

    @Column(name = "isActive")
    private String isActive;

}
