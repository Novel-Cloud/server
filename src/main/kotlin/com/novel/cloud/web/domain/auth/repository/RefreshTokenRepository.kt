package com.novel.cloud.web.domain.auth.repository

import com.novel.cloud.web.config.redis.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {
    fun findByRefreshToken(refreshToken: String?): RefreshToken?
}