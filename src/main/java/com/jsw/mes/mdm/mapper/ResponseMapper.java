package com.jsw.mes.mdm.mapper;


import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.base.BaseEntity;


public interface ResponseMapper<From extends BaseEntity, To> {
  To toResponse(From from);

//  ProcessMasterResponse toResponse(ProcessMaster processMaster, long primaryId, long secondaryId);
}
