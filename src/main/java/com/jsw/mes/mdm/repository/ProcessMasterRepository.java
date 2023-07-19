package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessMasterRepository extends JpaRepository<ProcessMaster,Integer> {

    Optional<ProcessMaster> findByProcessNameAndIsActive(String processName, String y);
}
