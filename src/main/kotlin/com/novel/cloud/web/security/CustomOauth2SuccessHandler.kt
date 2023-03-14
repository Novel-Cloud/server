package com.novel.cloud.web.security

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.detail.OAuthAttributes
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.Objects
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@RequiredArgsConstructor
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
        val oAuthAttributes = authentication.getPrincipal() as OAuthAttributes
        saveOrUpdate(oAuthAttributes)
    }

    private fun saveOrUpdate(oAuthAttributes: OAuthAttributes): Member? {
        var member: Member? = findMemberService.findByEmailOrElseNull(
            oAuthAttributes.email,
        )
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity()
            memberRepository.save<Member>(member)
        }
        member?.update(oAuthAttributes.picture)
        return member
    }

}