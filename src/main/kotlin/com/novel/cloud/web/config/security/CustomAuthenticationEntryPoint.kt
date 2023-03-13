package com.novel.cloud.web.config.security

import com.novel.cloud.web.config.security.jwt.JwtExceptionCode
import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_EXCEPTION
import com.novel.cloud.web.path.ApiPath
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {

        val exceptionCode: JwtExceptionCode = request.getAttribute(JWT_EXCEPTION) as JwtExceptionCode
        var encode: String = URLEncoder.encode("비정상적인 접근입니다.", "UTF-8")
        if (Objects.nonNull(exceptionCode)) {
            encode = URLEncoder.encode(exceptionCode.message, "UTF-8")
        }
        response.sendRedirect(ApiPath.ERROR_AUTH + "?message=" + encode)

    }
}