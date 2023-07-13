package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.AppMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppMasterRepository extends JpaRepository<AppMaster,Integer> {
}
