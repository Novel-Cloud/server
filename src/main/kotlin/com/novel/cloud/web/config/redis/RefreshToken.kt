package com.novel.cloud.web.config.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@Value("\${spring.security.jwt.refresh-token-validity-in-seconds}")
private const val REFRESH_TOKEN_TIME_TO_LIVE: Long = -1L

@RedisHash(value = "refreshToken", timeToLive = REFRESH_TOKEN_TIME_TO_LIVE)
class RefreshToken (

    @Id
    @Indexed
    val refreshToken: String,
    val memberId: Long,

)