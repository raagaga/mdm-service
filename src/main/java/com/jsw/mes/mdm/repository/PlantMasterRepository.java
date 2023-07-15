package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.PlantMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantMasterRepository extends JpaRepository<PlantMaster,Integer> {

   public PlantMaster findByPlantName(String plantName);

}
