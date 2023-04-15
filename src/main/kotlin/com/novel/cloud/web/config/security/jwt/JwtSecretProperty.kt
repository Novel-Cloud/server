package com.novel.cloud.web.config.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtSecretProperty (
    val secret: String,
    val header: String,
    val accessTokenValidityInSeconds: Long,
    val refreshTokenValidityInSeconds: Long,
)