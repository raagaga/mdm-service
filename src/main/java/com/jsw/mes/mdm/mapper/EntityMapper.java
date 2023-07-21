package com.jsw.mes.mdm.mapper;


import com.jsw.mes.mdm.entity.base.BaseEntity;

public interface EntityMapper<From, To extends BaseEntity> {
  To toEntity(From source);

}
