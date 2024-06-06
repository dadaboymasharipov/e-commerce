package com.example.appecommerce.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    //This Bean helps to return current User's id
    @Bean
    AuditorAware<UUID> auditorAware() {
        return new AuditingAwareImpl();
    }
}
