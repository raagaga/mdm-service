package com.jsw.mes.mdm.entity;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "MesProcessMst", schema = "user_service",uniqueConstraints={@UniqueConstraint(columnNames={"processName"})})
public class ProcessMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "processId")
    @Range(min=0,max=99999)
    private int processId;

    @Column(name = "processName")
    @Length(min=0,max = 15)
    private String processName;

    @Column(name = "processDesc")
    @Length(min=0,max = 100)
    private String processDescription;

    @Column(name = "isActive")
    private String isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "processId", referencedColumnName = "processId")
    private List< WorkCenterMaster > workCenterMstList = new ArrayList<WorkCenterMaster>();

}
