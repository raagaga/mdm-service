package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.AppMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<AppMaster,Long> {
    Optional<AppMaster> findByAppIdAndIsActive(long appId, String y);

    @Query("SELECT e from AppMaster e WHERE e.appName=:appName")
    AppMaster findByAppName(@Param("appName") String appName);

    @Query("SELECT e from AppMaster e WHERE e.appId=:appId")
    AppMaster findByAppId(@Param("appId") long appId);
}
