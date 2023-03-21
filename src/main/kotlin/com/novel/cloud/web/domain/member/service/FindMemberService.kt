package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.exception.NotFoundMemberException
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

    fun findByEmailOrElseThrow(email: String): Member {
        return memberRepository.findByEmail(email)
            .orElseThrow{ NotFoundMemberException() }
    }

    private fun findByIdOrElseThrow(memberId: Long): Member {
        return memberRepository.findById(memberId)
            .orElseThrow { NotFoundMemberException() }
    }

    fun findMemberSelf(memberContext: MemberContext?): FindMemberSelfRs? {
        val member = memberContext?.let {
            findLoginMember(it)
        }
        return FindMemberSelfRs.create(member)
    }

    fun findLoginMember(memberContext: MemberContext): Member? {
        val email = memberContext.email
        return findByEmailOrElseNull(email)
    }

    fun findLoginMemberOrElseThrow(memberContext: MemberContext): Member {
        val email = memberContext.email
        return findByEmailOrElseThrow(email);
    }

    fun findMemberProfile(memberId: Long): FindMemberSelfRs {
        val member = findByIdOrElseThrow(memberId)
        return FindMemberSelfRs.create(member)
    }

}