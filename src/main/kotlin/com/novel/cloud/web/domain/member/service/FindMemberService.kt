package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindMemberService(
    private val memberRepository: MemberRepository
) {

    fun findByEmailOrElseNull(email: String): Member? {
        return memberRepository.findByEmail(email)
            .orElse(null)
    }

    fun findMemberSelf(memberContext: MemberContext?): FindMemberSelfRs? {
        val member = memberContext?.let {
            findLoginMember(it)
        }
        return FindMemberSelfRs.create(member)
    }

    fun findLoginMember(memberContext: MemberContext): Member? {
        val email = memberContext.email
        return this.findByEmailOrElseNull(email)
    }

}