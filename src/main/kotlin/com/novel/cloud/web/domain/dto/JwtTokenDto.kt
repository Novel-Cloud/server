package com.novel.cloud.web.domain.dto

data class JwtTokenDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenValidity: String,
    val refreshTokenValidity: String
)