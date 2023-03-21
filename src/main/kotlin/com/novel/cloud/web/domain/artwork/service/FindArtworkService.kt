package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.PagedResponse
import com.novel.cloud.web.endpoint.Pagination
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class FindArtworkService (
    val findMemberService: FindMemberService,
    val artworkRepository: ArtworkRepository
) {

    fun findAllArtwork(pagination: Pagination): PagedResponse<FindArtworkRs> {
        val pageRequest = pagination.toPageRequest()
        val artworkList = artworkRepository.findArtworkList(pageRequest);
        val findArtworkRsList: List<FindArtworkRs> = artworkList.map { artwork ->
            FindArtworkRs.create(artwork)
        }.toList()
        return PagedResponse(
            pagination = pagination.copy(totalCount = artworkList.totalElements, totalPages = artworkList.totalPages),
            list = findArtworkRsList
        )
    }

}