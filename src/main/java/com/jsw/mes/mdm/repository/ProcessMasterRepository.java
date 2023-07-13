package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.ProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessMasterRepository extends JpaRepository<ProcessMaster,Integer> {
}
