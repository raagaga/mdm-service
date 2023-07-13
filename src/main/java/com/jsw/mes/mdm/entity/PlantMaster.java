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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MesPlantMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"plantName"})})
public class PlantMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plantId")
    @Range(min=0,max=99999)
    private int plantId;

    @Column(name = "plantName")
    @Length(min=0,max = 50)
    private String plantName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plantId", referencedColumnName = "plantId")
    private List <UnitMaster> unitMstList = new ArrayList <UnitMaster>();

}
