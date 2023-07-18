package com.jsw.mes.mdm.config;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.jsw.mes.mdm.entity.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "utcDateTimeProvider")
public class JpaConfig {
  @Bean
  public AuditorAware<Long> auditorAware() {
    return new AuditorAwareImpl();
  }

  @Bean
  public DateTimeProvider utcDateTimeProvider() {
    return () -> Optional.of(LocalDateTime.now(ZoneOffset.UTC));
  }
}
