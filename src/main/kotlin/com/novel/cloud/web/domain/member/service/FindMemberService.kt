package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.repository.MemberRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class FindMemberService(
    private val memberRepository: MemberRepository
) {

    fun findByEmailOrElseNull(email: String): Member? {
        return memberRepository.findByEmail(email)
            .orElse(null)
    }

    fun findMemberSelf(memberContext: MemberContext?): FindMemberSelfRs? {
        val member: Member? = this.findLoginMember(memberContext)
        return FindMemberSelfRs(member?.id, member?.nickname, member?.picture, member?.email)
    }
    fun findLoginMember(memberContext: MemberContext?): Member? {
        if (memberContext == null) {
            return null
        }
        val email: String = memberContext.email
        return this.findByEmailOrElseNull(email);
    }


}