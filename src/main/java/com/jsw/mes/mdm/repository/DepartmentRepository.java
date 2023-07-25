package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.DepartmentMaster;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentMaster, Integer> {
    Optional<DepartmentMaster> findByDepartmentNameAndIsActive(String departmentName, String y);

    Optional<DepartmentMaster> findByDepartmentIdAndIsActive(int departmentId, String y);
}
