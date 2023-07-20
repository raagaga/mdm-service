package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.UnitMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<UnitMaster,Integer>
{
    @Query("SELECT e from UnitMaster e WHERE e.unitName=:unitName")
    Optional<UnitMaster> getByUnitName(@Param("unitName") String unitName);

    Optional<UnitMaster> findByUnitIdAndIsActive(int id, String y);
}
