package com.novel.cloud.web.security

import lombok.RequiredArgsConstructor
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@RequiredArgsConstructor
@Transactional
class CustomOauth2UserService : DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        return super.loadUser(userRequest);
    }
}