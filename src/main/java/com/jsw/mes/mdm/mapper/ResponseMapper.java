package com.jsw.mes.mdm.mapper;


import com.jsw.mes.mdm.entity.base.BaseEntity;

public interface ResponseMapper<From extends BaseEntity, To> {

  To toResponse(From from);

  To toResponse(From from,long primaryId,long secondaryId);

}
