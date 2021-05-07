package com.zenza.pets.store.config

@Configuration
@AllArgsConstructor
class DataSourceConfig(val persistenceProperties: PersitencePostgresProperties) {

    @Bean
    fun dataSource(): DataSource {
        return  DataSourceBuilder.create()
                    .driverClassName(persistenceProperties.driverClassName)
                    .url(persistenceProperties.url)
                    .username(persistenceProperties.username)
                    .password(persistenceProperties.password)
                    .build()
    }

    @Bean
    fun namedParamaterJdbcTemplate(): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(this.dataSource())
    }

}