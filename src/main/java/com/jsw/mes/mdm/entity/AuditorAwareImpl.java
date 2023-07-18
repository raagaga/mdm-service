package com.jsw.mes.mdm.entity;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {
  @Override
  public Optional<Long> getCurrentAuditor() {
    Long userId = 1L;
    if (userId == -1L) return Optional.empty();
    else return Optional.of(userId);
  }
}
