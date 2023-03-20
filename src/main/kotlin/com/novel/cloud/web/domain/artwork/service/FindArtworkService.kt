package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.web.config.security.context.MemberContext
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

    fun findAllArtwork(memberContext: MemberContext, pagination: Pagination) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val pageRequest = pagination.toPageRequest()
        val artworkList = artworkRepository.findArtworkList(pageRequest);

//        val findPortfolioListRsList: List<FindPortfolioListRs> = myPortfolioList.stream()
//            .map { portfolio -> FindPortfolioListRs.create(portfolio, bookmarkedPortfolioIdSet) }
//            .collect(Collectors.toList())
//
//
//        pagination.setToalPages(artworkList.totalElements)
//        pagination.setTotalPages(artworkList.totalPages)
//        return PagedResponse.create(pagination, findPortfolioListRsList)
    }

}