package com.jsw.mes.mdm.model.request;

import com.jsw.mes.mdm.entity.base.BaseEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantRequest  {
  private int plantId;
  private String plantName;

  private String isActive;
}
