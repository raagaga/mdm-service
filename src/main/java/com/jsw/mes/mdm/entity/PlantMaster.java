package com.jsw.mes.mdm.entity;


import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
//@SuperBuilder
@Table(name = "MesPlantMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"plantName"})})
public class PlantMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plantId")
    private int plantId;

    @Column(name = "plantName")
    private String plantName;

    @Column(name = "isActive")
    private String isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plantId", referencedColumnName = "plantId")
    private List <UnitMaster> unitMstList = new ArrayList <UnitMaster>();

}
