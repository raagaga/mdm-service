package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.ScreenMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenMasterRepository extends JpaRepository<ScreenMaster,Integer> {
}
