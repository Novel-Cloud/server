package com.novel.cloud.web.domain.member.service

import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.bookmark.service.FindBookmarkService
import com.novel.cloud.web.domain.member.controller.rs.FindMemberRs
import com.novel.cloud.web.domain.member.repository.MemberRepository
import com.novel.cloud.web.exception.NotFoundMemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindMemberService(
    private val memberRepository: MemberRepository,
    private val findBookmarkService: FindBookmarkService,
) {

    fun findById(id: Long): Member? {
        return memberRepository.findById(id)
            .orElse(null)
    }

    private fun findByIdOrElseThrow(memberId: Long): Member {
        return memberRepository.findById(memberId)
            .orElseThrow { NotFoundMemberException() }
    }

    fun findByEmailOrElseNull(email: String): Member? {
        return memberRepository.findByEmail(email)
            .orElse(null)
    }

    fun findByEmailOrElseThrow(email: String): Member {
        return memberRepository.findByEmail(email)
            .orElseThrow { NotFoundMemberException() }
    }

    fun findLoginMember(memberContext: MemberContext?): Member? {
        return memberContext?.let {
            val email = memberContext.email
            findByEmailOrElseNull(email)
        }
    }

    fun findLoginMemberOrElseThrow(memberContext: MemberContext): Member {
        val email = memberContext.email
        return findByEmailOrElseThrow(email)
    }

    /**
     * 내 정보 불러오기
     */
    fun findMemberSelf(memberContext: MemberContext?): FindMemberRs? {
        val member = memberContext?.let { findLoginMember(it) }

        val myBookmarkedArtworkIdSet = memberContext?.let { getMyBookmarkedArtworkIdSet(it) } ?: emptySet()

        return FindMemberRs.create(member, myBookmarkedArtworkIdSet)
    }

    /**
     * 다른 멤버 정보 불러오기
     */
    fun findMemberProfile(memberContext: MemberContext?, memberId: Long): FindMemberRs {
        val member = findByIdOrElseThrow(memberId)

        val myBookmarkedArtworkIdSet = memberContext?.let { getMyBookmarkedArtworkIdSet(it) } ?: emptySet()

        return FindMemberRs.create(member, myBookmarkedArtworkIdSet)
    }

    private fun getMyBookmarkedArtworkIdSet(memberContext: MemberContext): Set<Long?> {
        val member = findLoginMemberOrElseThrow(memberContext)
        return findBookmarkService.findByMember(member).map { bookmark ->
            bookmark.artwork.id
        }.toSet()
    }

}