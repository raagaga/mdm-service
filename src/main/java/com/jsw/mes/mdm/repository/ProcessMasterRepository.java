package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.exception.ProcessMasterException;
import com.jsw.mes.mdm.model.response.QueryResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessMasterRepository extends JpaRepository<ProcessMaster,Integer> {

    Optional<ProcessMaster> findByProcessNameAndIsActive(String processName, String y);

}
