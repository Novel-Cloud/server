package com.novel.cloud.web.domain.bookmark.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.bookmark.Bookmark
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.enums.ArtworkType
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.service.FindArtworkService
import com.novel.cloud.web.domain.bookmark.controller.rq.BookmarkArtworkRq
import com.novel.cloud.web.domain.bookmark.repository.BookmarkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BookmarkServiceTest {

    private lateinit var findMemberService: FindMemberService
    private lateinit var findArtworkService: FindArtworkService
    private lateinit var findBookmarkService: FindBookmarkService
    private lateinit var bookmarkRepository: BookmarkRepository
    private lateinit var bookmarkService: BookmarkService

    private lateinit var memberContext: MemberContext
    private lateinit var member: Member

    @BeforeEach
    fun setUp() {
        memberContext = MemberContext(
            email="example@gmail.com",
            registrationId="google"
        )

        member = Member(
            email = "이메일",
            nickname = "닉넴",
            picture = "내 사진"
        )

        findMemberService = mockk()
        findArtworkService = mockk()
        findBookmarkService = mockk()
        bookmarkRepository = mockk()
        bookmarkService = BookmarkService(
            findMemberService,
            findArtworkService,
            findBookmarkService,
            bookmarkRepository,
        )
    }

    @Test
    fun `이미 누른 좋아요가 있으면 좋아요를 삭제한다`() {
        // given
        val body = BookmarkArtworkRq(
            artworkId = 1
        )

        val artwork = Artwork(
            title = "타이틀",
            content = "내용",
            writer = member,
            artworkType = ArtworkType.NOVEL,
            tags = setOf()
        )

        val bookmark = Bookmark(
            member = member,
            artwork = artwork
        )

        every { findMemberService.findLoginMemberOrElseThrow(memberContext) }.returns( member )
        every { findArtworkService.findByIdOrElseThrow(1) }.returns( artwork )
        every { findBookmarkService.findByMemberAndArtworkOrElseNull(member, artwork) }.returns( bookmark )
        every { bookmarkRepository.delete(bookmark) }.returns(mockk())

        // when
        bookmarkService.toggleArtworkBookmark(memberContext, body)

        // then
        verify { bookmarkRepository.delete(bookmark) }
        confirmVerified()
    }

    @Test
    fun `좋아요를 누르지 않았다면 좋아요를 생성한다`() {
        // given
        val body = BookmarkArtworkRq(
            artworkId = 1
        )

        val artwork = Artwork(
            title = "타이틀",
            content = "내용",
            writer = member,
            artworkType = ArtworkType.NOVEL,
            tags = setOf()
        )

        val bookmark = Bookmark(
            member = member,
            artwork = artwork
        )

        every { findMemberService.findLoginMemberOrElseThrow(memberContext) }.returns( member )
        every { findArtworkService.findByIdOrElseThrow(1) }.returns( artwork )
        every { findBookmarkService.findByMemberAndArtworkOrElseNull(member, artwork) }.returns( null )
        every { bookmarkRepository.save(any()) }.returns(mockk())

        // when
        bookmarkService.toggleArtworkBookmark(memberContext, body)

        // then
        verify { bookmarkRepository.save(any()) }
        confirmVerified()
    }


}