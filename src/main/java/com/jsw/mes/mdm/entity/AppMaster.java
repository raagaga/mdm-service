package com.jsw.mes.mdm.entity;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Length;
import lombok.AllArgsConstructor;
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
@Table(name = "MesAppMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"appName"})})
public class AppMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appId")
    @Range(min=0,max=999999999)
    private long appId;

    @Column(name = "appName")
    @Length(min=0,max = 15)
    private String appName;

    @Column(name = "appDesc")
    @Length(min=0,max = 100)
    private String appDescription;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "appId", referencedColumnName = "appId")
    private List<UnitMaster> unitMstList = new ArrayList<UnitMaster>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "appId", referencedColumnName = "appId",unique = true)
    private List< ProcessMaster > processMstList = new ArrayList<ProcessMaster>();
}
