package com.novel.cloud.web.domain.auth.controller.rs

data class RefreshTokenRs (
    val refreshToken: String,
    val validity: String
)