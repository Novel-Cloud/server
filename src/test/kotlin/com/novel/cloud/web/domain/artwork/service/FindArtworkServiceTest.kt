package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.PaginationRq
import com.novel.cloud.web.domain.artwork.controller.rq.SearchArtworkFilterRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.bookmark.service.FindBookmarkService

import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.endpoint.Pagination
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach


import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl

class FindArtworkServiceTest {

    private lateinit var findArtworkService: FindArtworkService
    private lateinit var findMemberService: FindMemberService
    private lateinit var findBookmarkService: FindBookmarkService
    private lateinit var artworkRepository: ArtworkRepository
    private lateinit var temporaryArtworkRepository: TemporaryArtworkRepository

    lateinit var member: Member
    lateinit var memberContext: MemberContext

    @BeforeEach
    fun setUp() {
        memberContext = MemberContext(
            email="min050410@gmail.com",
            registrationId="google"
        )

        member = Member(
            email = "이메일",
            nickname = "닉넴",
            picture = "내 사진"
        )

        findMemberService = mockk()
        findBookmarkService = mockk()
        artworkRepository = mockk()
        temporaryArtworkRepository = mockk()

        findArtworkService = FindArtworkService(
            findMemberService,
            findBookmarkService,
            artworkRepository,
            temporaryArtworkRepository
        )
    }

    @Test
    fun `유저는 필터 검색이 가능하다`() {

        // given
        val paginationRq = PaginationRq(
            page = 1,
            size = 2
        )

        val searchArtworkListRq = SearchArtworkFilterRq(
            search = "",
            uploadDateType = null,
            artworkType = ArtworkType.NOVEL,
            sortType = null
        )

        val artwork = Artwork(
            title = "work",
            content = "콘텐스",
            writer = member,
            artworkType = ArtworkType.NOVEL,
            tags = setOf<Tag>(Tag("tag1", member))
        )

        val bookmark = Bookmark(
            member = member,
            artwork = artwork
        )

        val pagination = Pagination(paginationRq.page, paginationRq.size)
        val pageRequest = pagination.toPageRequest()

        every { findMemberService.findLoginMember(memberContext) }.returns( member )
        every { artworkRepository.findArtworkListByFilter(pageRequest, searchArtworkListRq) }.returns( PageImpl(listOf(artwork), pageRequest, 1) )
        every { findBookmarkService.findByMember(member) }.returns( listOf(bookmark) )

        // when & then
        val pagedFindArtworkRs = findArtworkService.searchArtworkByFilter(memberContext, pagination, searchArtworkListRq)
        Assertions.assertEquals(pagedFindArtworkRs.list!![0].title, "work")
    }

}