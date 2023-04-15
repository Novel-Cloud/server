package com.novel.cloud.web.domain.auth.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
data class GoogleOAuthConfigProperties (
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)