package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.WorkCenterMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkCenterRepository extends JpaRepository<WorkCenterMaster,Integer> {

    Optional<WorkCenterMaster> findByWorkCenterNameAndIsActive(String workCenterName,String y);

    Optional<WorkCenterMaster> findByWorkCenterIdAndIsActive(int workCenterId, String y);
}
