package com.novel.cloud.web.config.security.jwt

import com.novel.cloud.web.config.security.jwt.JwtProperty.JWT_EXCEPTION
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import lombok.RequiredArgsConstructor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@RequiredArgsConstructor
class JwtOncePerRequestFilter(
    private val jwtProvider: JwtProvider,
    jwtSecretProperty: JwtSecretProperty,
) : OncePerRequestFilter() {

    val HEADER = jwtSecretProperty.header

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = request.getHeader(HEADER)
        try {
            authenticate(token)
        } catch (e: ExpiredJwtException) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.EXPIRED)
        } catch (e: UnsupportedJwtException) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.UNSUPPORTED)
        } catch (e: MalformedJwtException) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.MALFORMED)
        } catch (e: SignatureException) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID_SIGNATURE)
        } catch (e: IllegalArgumentException) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID)
        }
        filterChain.doFilter(request, response)
    }

    private fun authenticate(token: String?) {
        token?.let {
            val auth = jwtProvider.getAuthentication(it)
            SecurityContextHolder.getContext().authentication = auth
        }
    }

}