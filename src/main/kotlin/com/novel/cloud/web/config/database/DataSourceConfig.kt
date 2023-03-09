package com.novel.cloud.web.config.database

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

//    @Value("\${spring.datasource.hikari.jdbc-url}")
//    private lateinit var jdbcUrl: String;
//
//    @Value("\${spring.datasource.hikari.username}")
//    private lateinit var username: String
//
//    @Value("\${spring.datasource.hikari.password}")
//    private lateinit var password: String

    @Bean()
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): DataSource? {
        return DataSourceBuilder.create()
                .type(HikariDataSource::class.java)
                .build()
    }

}