package com.jsw.mes.mdm.entity;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
//import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

//import javax.validation.constraints.Length;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MesUnitMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"unitName"})})
public class UnitMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unitId")
    @Range(min=0,max=99999)
    private int unitId;

    @Column(name = "unitName",unique = true)
    @Length(min=0,max = 15)
    private String unitName;

    @Column(name = "unitDesc")
    @Length(min=0,max = 100)
    private String unitDescription;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "unitId", referencedColumnName = "unitId")
    private List<ProcessMaster> processMstList = new ArrayList<ProcessMaster>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "unitId", referencedColumnName = "unitId",unique = true)
    private List<WorkCenterMaster> workCenterMstList = new ArrayList<WorkCenterMaster>();

}
