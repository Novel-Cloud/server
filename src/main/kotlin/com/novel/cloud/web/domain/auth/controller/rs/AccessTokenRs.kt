package com.novel.cloud.web.domain.auth.controller.rs

data class AccessTokenRs (
    val accessToken: String,
    val validity: String
)