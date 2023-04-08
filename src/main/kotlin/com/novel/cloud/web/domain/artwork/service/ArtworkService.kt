package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.db.entity.tag.Tag
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.AutoSaveTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.ArtworkTagRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.exception.DoNotHavePermissionToAutoSaveArtwork
import com.novel.cloud.web.utils.DateUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkService(
    private val findMemberService: FindMemberService,
    private val findArtworkService: FindArtworkService,
    private val artworkRepository: ArtworkRepository,
    private val temporaryArtworkRepository: TemporaryArtworkRepository,
    private val artworkTagRepository: ArtworkTagRepository
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
        artworkRepository.save(artwork)
        createTags(artwork, member, tags)
        return artwork;
    }

    private fun createTags(artwork: Artwork, member: Member, tags: List<String>) {
        tags.map { content ->
            val tag = Tag(
                content = content,
                writer = member,
                artwork = artwork
            )
            artworkTagRepository.save(tag)
        }
    }

    private fun createTemporaryArtwork(memberContext: MemberContext, rq: AutoSaveTemporaryArtworkRq): TemporaryArtwork {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = TemporaryArtwork(
            content = rq.content,
            writer = member
        )
        return temporaryArtworkRepository.save(temporaryArtwork)
    }

    fun autoSaveArtwork(memberContext: MemberContext, rq: AutoSaveTemporaryArtworkRq): String {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val temporaryArtwork = findArtworkService.findTemporaryArtworkByWriterOrElseNull(member)
        temporaryArtwork?.let {
            autoSavePermissionCheck(member, temporaryArtwork)
            temporaryArtwork.updateContent(rq.content)
            return getAutoSaveResponseString()
        }
        this.createTemporaryArtwork(memberContext, rq)
        return getAutoSaveResponseString()
    }

    private fun autoSavePermissionCheck(member: Member, temporaryArtwork: TemporaryArtwork) {
        if (!temporaryArtwork.writer.equals(member)) {
            throw DoNotHavePermissionToAutoSaveArtwork()
        }
    }

    private fun getAutoSaveResponseString(): String {
        return DateUtils.formatedNow() + "에 자동 저장 되었습니다."
    }

    fun artworkAddView(artwork: Artwork) {
        artwork.addView()
    }


}