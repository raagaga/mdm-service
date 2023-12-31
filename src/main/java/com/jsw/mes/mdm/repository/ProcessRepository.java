package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.ProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessMaster,Integer> {

    Optional<ProcessMaster> findByProcessNameAndIsActive(String processName, String y);

    Optional<ProcessMaster> findByProcessIdAndIsActive(int processId, String y);
}
