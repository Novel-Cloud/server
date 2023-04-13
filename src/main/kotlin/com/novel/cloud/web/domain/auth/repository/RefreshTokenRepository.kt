package com.novel.cloud.web.domain.auth.repository

import com.novel.cloud.web.config.redis.RedisRefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RedisRefreshToken, String> {
    fun findByRefreshToken(refreshToken: String?): RedisRefreshToken?

}