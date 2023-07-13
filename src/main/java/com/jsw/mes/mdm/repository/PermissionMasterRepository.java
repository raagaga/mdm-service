package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.PermissionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMasterRepository extends JpaRepository<PermissionMaster,Integer> {
}
