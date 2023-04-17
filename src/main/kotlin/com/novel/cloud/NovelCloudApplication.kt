package com.novel.cloud

import com.novel.cloud.web.config.security.jwt.JwtSecretProperty
import com.novel.cloud.web.domain.auth.service.GoogleOAuthConfigProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    JwtSecretProperty::class,
    GoogleOAuthConfigProperties::class,
)
class NovelCloudApplication

fun main(args: Array<String>) {
    runApplication<NovelCloudApplication>(*args)
}
