package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.PlantMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<PlantMaster, Integer> {


  Optional<PlantMaster> findByPlantNameAndIsActive(String plantName, String isActive);

  List<PlantMaster> findAllByIsActive(String isActive);

  Optional<PlantMaster> findByPlantIdAndIsActive(int plantId, String isActive);

  @Query(value = "update mes_plant_mst set isActive='N' where plantId=:plantId",nativeQuery = true)
  PlantMaster updatePlant(int plantId);
}
