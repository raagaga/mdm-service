package com.jsw.mes.mdm.repository;

import com.jsw.mes.mdm.entity.ScreenMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<ScreenMaster,Integer> {

    Optional<ScreenMaster> findByScreenNameAndIsActive(String screenName, String y);

    List<ScreenMaster> findByParentIdAndIsActive(int parentId, String y);

    Optional<ScreenMaster> findByScreenIdAndIsActive(int screenId,String y);
}
