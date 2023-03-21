package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkDetailRs
import com.novel.cloud.web.domain.artwork.controller.rs.FindArtworkRs
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.PagedResponse
import com.novel.cloud.web.endpoint.Pagination
import com.novel.cloud.web.exception.NotFoundArtworkException
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

    fun findArtworkDetail(artworkId: Long): FindArtworkDetailRs {
        val artwork = findArtworkByIdOrElseThrow(artworkId)
        return FindArtworkDetailRs.create(artwork)
    }

    private fun findArtworkByIdOrElseThrow(artworkId: Long): Artwork {
        return artworkRepository.findById(artworkId)
            .orElseThrow{ NotFoundArtworkException() }
    }

}