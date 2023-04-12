package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.SearchArtworkFilterRq
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkDetailRs
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs
import com.novel.cloud.web.domain.artwork.controller.rs.FindTemporaryArtworkRs
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.bookmark.service.FindBookmarkService
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.PagedResponse
import com.novel.cloud.web.endpoint.Pagination
import com.novel.cloud.web.exception.NotFoundArtworkException
import com.novel.cloud.web.exception.NotFoundTemporaryArtworkException
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindArtworkService(
    private val findMemberService: FindMemberService,
    private val findBookmarkService: FindBookmarkService,
    private val artworkRepository: ArtworkRepository,
    private val temporaryArtworkRepository: TemporaryArtworkRepository,
) {

    fun findByIdOrElseThrow(artworkId: Long): Artwork {
        return artworkRepository.findById(artworkId)
            .orElseThrow { NotFoundArtworkException() }
    }

    fun getBookmarkYn(loginMember: Member?, artwork: Artwork): Boolean {
        val bookmarkYn = loginMember
            ?.let {
                findBookmarkService.findByMember(loginMember).map { bookmark ->
                    bookmark.artwork.id
                }.toSet()
            }?.contains(artwork.id) ?: false
        return bookmarkYn
    }

    /**
     * 작품 전체 목록 조회
     */
    fun findAllArtwork(memberContext: MemberContext?, pagination: Pagination): PagedResponse<FindArtworkRs> {
        val pageRequest = pagination.toPageRequest()

        val artworkList = artworkRepository.findArtworkList(pageRequest)
        return getFindArtworkPagedResponseList(memberContext, artworkList, pagination)
    }

    /**
     * 작품 단건 조회
     */
    fun findArtworkDetail(memberContext: MemberContext?, artworkId: Long): FindArtworkDetailRs {
        val artwork = findByIdOrElseThrow(artworkId)
        val loginMember = findMemberService.findLoginMember(memberContext)

        val bookmarkYn = getBookmarkYn(loginMember, artwork)
        return FindArtworkDetailRs.create(artwork, bookmarkYn)
    }

    /**
     * 내 임시 저장 작품 불러오기
     */
    fun findTemporaryArtworkSelf(memberContext: MemberContext): FindTemporaryArtworkRs {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = temporaryArtworkRepository.findByWriter(member)
            ?: throw NotFoundTemporaryArtworkException()

        return FindTemporaryArtworkRs.create(temporaryArtwork)
    }

    /**
     * 작품 해시 태그 검색
     */
    fun searchArtworkByTag(
        memberContext: MemberContext?,
        pagination: Pagination,
        tags: List<String>,
    ): PagedResponse<FindArtworkRs> {
        val pageRequest = pagination.toPageRequest()
        val artworkList = artworkRepository.findArtworkListByTag(pageRequest, tags)
        return getFindArtworkPagedResponseList(memberContext, artworkList, pagination)
    }

    /**
     * 작품 필터 검색
     */
    fun searchArtworkByFilter(
        memberContext: MemberContext?,
        pagination: Pagination,
        filter: SearchArtworkFilterRq,
    ): PagedResponse<FindArtworkRs> {
        val pageRequest = pagination.toPageRequest()
        val artworkList = artworkRepository.findArtworkListByFilter(pageRequest, filter)
        return getFindArtworkPagedResponseList(memberContext, artworkList, pagination)
    }

    /**
     * 작품 페이징 리스트
     */
    fun getFindArtworkPagedResponseList(
        memberContext: MemberContext?,
        artworkList: Page<Artwork>,
        pagination: Pagination,
    ): PagedResponse<FindArtworkRs> {
        val loginMember = findMemberService.findLoginMember(memberContext)
        val findArtworkRsList: List<FindArtworkRs> = artworkList.map { artwork ->
            val bookmarkYn = getBookmarkYn(loginMember, artwork)
            FindArtworkRs.create(artwork, bookmarkYn)
        }.toList()

        return PagedResponse(
            pagination = pagination.copy(
                totalCount = artworkList.totalElements,
                totalPages = artworkList.totalPages
            ),
            list = findArtworkRsList
        )
    }

}