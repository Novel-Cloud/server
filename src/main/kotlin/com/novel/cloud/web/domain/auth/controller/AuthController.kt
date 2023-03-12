package com.novel.cloud.web.domain.auth.controller

import com.novel.cloud.web.domain.auth.controller.rq.OAuthRq
import com.novel.cloud.web.domain.dto.JwtTokenDto
import com.novel.cloud.web.path.ApiPath
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequiredArgsConstructor
class AuthController {


    @PostMapping(ApiPath.LOGIN_OAUTH2)
    fun loginOAuth2(@Validated @RequestBody rq: OAuthRq?): JwtTokenDto? {
        return oAuth2Service.loginOAuth2(rq)
    }

    @PostMapping(ApiPath.REFRESH_TOKEN)
    fun refreshToken(@AuthenticationPrincipal memberContext: MemberContext?): JwtTokenDto? {
        return authService.refreshToken(memberContext)
    }

}