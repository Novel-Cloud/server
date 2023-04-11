package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.domain.bookmark.service.FindBookmarkService
import com.novel.cloud.web.domain.member.controller.rs.FindMemberSelfRs
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.exception.NotFoundMemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindMemberService(
    private val memberRepository: MemberRepository,
    private val findBookmarkService: FindBookmarkService
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
        val myBookmarkedArtworkIdSet = getMyBookmarkedArtworkIdSet(memberContext)
        return FindMemberSelfRs.create(member, myBookmarkedArtworkIdSet)
    }

    fun findLoginMember(memberContext: MemberContext?): Member? {
        return memberContext?.let {
            val email = memberContext.email
            findByEmailOrElseNull(email)
        }
    }

    fun findLoginMemberOrElseThrow(memberContext: MemberContext): Member {
        val email = memberContext.email
        return findByEmailOrElseThrow(email);
    }

    fun findLoginMemberOrElseNull(memberContext: MemberContext): Member? {
        val email = memberContext.email
        return findByEmailOrElseNull(email);
    }

    fun findMemberProfile(memberContext: MemberContext, memberId: Long): FindMemberSelfRs {
        val member = findByIdOrElseThrow(memberId)
        val myBookmarkedArtworkIdSet = getMyBookmarkedArtworkIdSet(memberContext)
        return FindMemberSelfRs.create(member, myBookmarkedArtworkIdSet)
    }

    private fun getMyBookmarkedArtworkIdSet(memberContext: MemberContext?): Set<Long?> {
        memberContext?.let {
            val member = findLoginMemberOrElseThrow(memberContext)
            return findBookmarkService.findByMember(member).map { bookmark ->
                bookmark.artwork.id
            }.toSet()
        }
        return HashSet()
    }

}