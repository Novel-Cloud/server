package com.novel.cloud.web.security

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomOauth2SuccessHandler(
    private val memberRepository: MemberRepository,
    private val findMemberService: FindMemberService
): AuthenticationSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        val oAuthAttributes = authentication.principal as OAuthAttributes
        saveOrUpdate(oAuthAttributes)
    }

    private fun saveOrUpdate(oAuthAttributes: OAuthAttributes): Member? {
        val member = findMemberService.findByEmailOrElseNull(oAuthAttributes.email)
            ?: memberRepository.save(oAuthAttributes.toEntity())
        member.update(oAuthAttributes.picture)
        return member
    }

}