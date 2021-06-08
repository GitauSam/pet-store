package com.zenza.pets.store.config

import lombok.AllArgsConstructor
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
@AllArgsConstructor
class DataSourceConfig {

    @Bean
    fun dataSource(): DataSource {
        return  DataSourceBuilder.create()
                    .driverClassName("org.postgresql.Driver")
                    .url("jdbc:postgresql://localhost:5432/pets")
                    .username("phi")
                    .password("phi2021")
                    .build()
    }

    @Bean
    fun namedParamaterJdbcTemplate(): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(this.dataSource())
    }

}