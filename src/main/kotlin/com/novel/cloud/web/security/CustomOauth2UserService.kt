package com.novel.cloud.web.security

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomOauth2UserService : DefaultOAuth2UserService() {

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        // 다른 oauth2 로그인 서비스 추가시 custom 추가
        return super.loadUser(userRequest)
    }

}