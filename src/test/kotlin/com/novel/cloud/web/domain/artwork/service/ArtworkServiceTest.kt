package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.AutoSaveTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.domain.tag.service.ArtworkTagService
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class ArtworkServiceTest {

    private lateinit var artworkService: ArtworkService
    private lateinit var findMemberService: FindMemberService
    private lateinit var findArtworkService: FindArtworkService
    private lateinit var artworkTagService: ArtworkTagService
    private lateinit var artworkRepository: ArtworkRepository
    private lateinit var temporaryArtworkRepository: TemporaryArtworkRepository

    private lateinit var memberContext: MemberContext
    private lateinit var member: Member

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
        findArtworkService = mockk()
        artworkTagService = mockk()
        artworkRepository = mockk()
        temporaryArtworkRepository = mockk()
        artworkService = ArtworkService(
            findMemberService,
            findArtworkService,
            artworkTagService,
            artworkRepository,
            temporaryArtworkRepository
        )
    }

    @Test
    fun `내 임시 저장 작품이 있는 경우 업데이트가 가능하다`() {
        // given
        val body = AutoSaveTemporaryArtworkRq(
            content = "난 멋쟁이"
        )

        val temporaryArtwork = TemporaryArtwork(
            content = "난 멋쟁쓰",
            writer = member
        )

        every { findMemberService.findLoginMemberOrElseThrow(memberContext) }.returns(member)
        every { temporaryArtworkRepository.findByWriter(member) }.returns( temporaryArtwork )

        // when
        artworkService.autoSaveArtwork(memberContext, body)

        // then
        Assertions.assertEquals(temporaryArtwork.content, "난 멋쟁이")
    }

    @Test
    fun `내 임시 저장된 작품이 없으면 새로운 객체를 저장한다`() {
        // given
        val body = AutoSaveTemporaryArtworkRq(
            content = "난 멋쟁이"
        )

        val temporaryArtwork = TemporaryArtwork(
            content = body.content,
            writer = member
        )

        every { findMemberService.findLoginMemberOrElseThrow(memberContext) }.returns(member)
        every { temporaryArtworkRepository.findByWriter(member) }.returns( null )
        every { temporaryArtworkRepository.save(any()) } returns temporaryArtwork

        // when
        artworkService.autoSaveArtwork(memberContext, body)

        // then
        verify { temporaryArtworkRepository.save(any()) }
        confirmVerified()
    }

}