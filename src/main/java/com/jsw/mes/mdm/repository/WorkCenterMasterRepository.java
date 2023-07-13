package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.WorkCenterMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCenterMasterRepository extends JpaRepository<WorkCenterMaster,Integer> {
}
