package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.UnitMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitMasterRepository extends JpaRepository<UnitMaster,Integer> {
}
