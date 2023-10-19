package com.wayon.transferencia;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.flywaydb.core.Flyway;
@Configuration
public class EmptyMigrationStrategyConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy(Flyway flyway){
        return strategy -> {flyway.migrate();};
    }
}
