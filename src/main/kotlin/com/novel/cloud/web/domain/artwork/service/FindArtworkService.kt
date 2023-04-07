package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindArtworkService(
    val findMemberService: FindMemberService,
    val findBookmarkService: FindBookmarkService,
    val artworkRepository: ArtworkRepository,
    val temporaryArtworkRepository: TemporaryArtworkRepository,
) {

    fun findAllArtwork(memberContext: MemberContext?, pagination: Pagination): PagedResponse<FindArtworkRs> {
        val pageRequest = pagination.toPageRequest()
        val artworkList = artworkRepository.findArtworkList(pageRequest);
        val findArtworkRsList: List<FindArtworkRs> = artworkList.map { artwork ->
            val bookmarkYn = getBookmarkYn(memberContext, artwork)
            FindArtworkRs.create(artwork, bookmarkYn)
        }.toList()
        return PagedResponse(
            pagination = pagination.copy(totalCount = artworkList.totalElements, totalPages = artworkList.totalPages),
            list = findArtworkRsList
        )
    }

    // TODO::FindArtwork, Artwork 병합
    @Transactional
    fun findArtworkDetail(memberContext: MemberContext?, artworkId: Long): FindArtworkDetailRs {
        val artwork = findByIdOrElseThrow(artworkId)
        val bookmarkYn = getBookmarkYn(memberContext, artwork)
        artwork.addView()
        return FindArtworkDetailRs.create(artwork, bookmarkYn)
    }

    fun getBookmarkYn(memberContext: MemberContext?, artwork: Artwork): Boolean {
        val bookmarkedArtworkIdSet = getMyBookmarkedArtworkIdSet(memberContext)
        return bookmarkedArtworkIdSet.contains(artwork.id)
    }

    fun getMyBookmarkedArtworkIdSet(memberContext: MemberContext?): Set<Long?> {
        memberContext?.let {
            val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
            return findBookmarkService.findByMember(member).map { bookmark ->
                bookmark.artwork.id
            }.toSet()
        }
        return HashSet()
    }

    fun findByIdOrElseThrow(artworkId: Long): Artwork {
        return artworkRepository.findById(artworkId)
            .orElseThrow{ NotFoundArtworkException() }
    }

    fun findTemporaryArtworkByWriterOrElseNull(writer: Member): TemporaryArtwork? {
        return temporaryArtworkRepository.findByWriter(writer)
            .orElse(null)
    }

    fun findTemporaryArtworkSelf(memberContext: MemberContext): FindTemporaryArtworkRs {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = findTemporaryArtworkByWriterOrElseNull(member)
        return FindTemporaryArtworkRs.create(temporaryArtwork)
    }

}