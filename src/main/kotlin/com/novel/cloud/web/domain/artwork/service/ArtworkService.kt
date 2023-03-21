package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.CreateTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.UpdateTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.DoNotHavePermissionToAutoSaveArtwork
import com.novel.cloud.web.exception.NotFoundTemporaryArtworkException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkService(
    private val findMemberService: FindMemberService,
    private val artworkRepository: ArtworkRepository,
    private val temporaryArtworkRepository: TemporaryArtworkRepository
) {
    fun submitArtwork(memberContext: MemberContext, rq: CreateArtworkRq): Artwork {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val tags = rq.tags
        val artwork = Artwork(
            title = rq.title,
            content = rq.content,
            writer = member,
            artworkType = rq.artworkType
        )
        createTags(artwork, tags)
        return artworkRepository.save(artwork)
    }

    private fun createTags(artwork: Artwork, tags: List<String>) {
        tags.map {
            val tag = Tag(it)
            artwork.addTag(tag)
        }
    }

    fun createArtwork(memberContext: MemberContext, rq: CreateTemporaryArtworkRq): Long? {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = TemporaryArtwork(
            content = rq.content,
            writer = member
        )
        temporaryArtworkRepository.save(temporaryArtwork)
        return temporaryArtwork.id
    }

    fun autoSaveArtwork(memberContext: MemberContext, rq: UpdateTemporaryArtworkRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = findTemporaryArtworkByIdOrElseThrow(rq.temporaryArtworkId)


    }

    private fun findTemporaryArtworkByIdOrElseThrow(temporaryArtworkId: Long): TemporaryArtwork {
        return temporaryArtworkRepository.findById(temporaryArtworkId)
            .orElseThrow{ NotFoundTemporaryArtworkException() }
    }

    private fun autoSavePermissionCheck(temporaryArtwork: TemporaryArtwork, member: Member) {
        if (!temporaryArtwork.writer.equals(member)) {
            throw DoNotHavePermissionToAutoSaveArtwork()
        }
    }

}