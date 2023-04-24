package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.TemporaryArtwork
import com.novel.cloud.db.entity.member.Member
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.AutoSaveTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.DeleteArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.UpdateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.UpdateArtworkViewRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.artwork.repository.TemporaryArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import com.novel.cloud.web.domain.tag.service.ArtworkTagService
import com.novel.cloud.web.exception.DoNotHavePermissionToDeleteOrUpdateArtworkException
import com.novel.cloud.web.utils.DateUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkService(
    private val findMemberService: FindMemberService,
    private val findArtworkService: FindArtworkService,
    private val artworkTagService: ArtworkTagService,
    private val artworkRepository: ArtworkRepository,
    private val temporaryArtworkRepository: TemporaryArtworkRepository,
) {

    /**
     * 작품 등록
     */
    fun submitArtwork(memberContext: MemberContext, rq: CreateArtworkRq): Artwork {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)

        // [1] 태그 중복 제거 후 저장
        val tagContents = rq.tags.distinct()
        val tags = artworkTagService.createTags(member, tagContents)

        val artwork = Artwork(
            title = rq.title,
            content = rq.content,
            writer = member,
            artworkType = rq.artworkType,
            tags = tags
        )

        // [2] 글 저장
        artworkRepository.save(artwork)
        return artwork
    }

    /**
     * 작품 수정
     */
    fun updateArtwork(memberContext: MemberContext, rq: UpdateArtworkRq): Artwork {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val artwork = findArtworkService.findByIdOrElseThrow(rq.artworkId)

        val tagContents = rq.tags.distinct()
        val beforeTags = artwork.tags

        val tags = artworkTagService.createTags(member, tagContents)

        artwork.update(
            title = rq.title,
            content = rq.content,
            artworkType = rq.artworkType,
            tags = tags
        )

        artworkTagService.removeTags(member, beforeTags)
        return artwork
    }

    /**
     * 작품 삭제
     */
    fun deleteArtwork(memberContext: MemberContext, rq: DeleteArtworkRq) {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
        val artwork = findArtworkService.findByIdOrElseThrow(rq.artworkId)
        val tags = artwork.tags

        artworkPermissionCheck(member, artwork)
        artworkRepository.delete(artwork)

        artworkTagService.removeTags(member, tags)
    }

    private fun artworkPermissionCheck(member: Member, artwork: Artwork) {
        val writerId: Long? = artwork.writer.id
        val memberId: Long? = member.id
        if (writerId != memberId) {
            throw DoNotHavePermissionToDeleteOrUpdateArtworkException()
        }
    }


    /**
     * 작품 조회수 증가
     */
    fun updateArtworkView(rq: UpdateArtworkViewRq) {
        val artworkId = rq.artworkId
        artworkId?.let {
            val artwork = findArtworkService.findByIdOrElseThrow(artworkId)
            artwork.addView()
        }
    }

    /**
     * 임시 작품 자동 저장
     */
    fun autoSaveArtwork(memberContext: MemberContext, rq: AutoSaveTemporaryArtworkRq): String {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)

        val temporaryArtwork = temporaryArtworkRepository.findByWriter(member)

        // 이미 있는 경우 update 없으면 create
        temporaryArtwork?.let {
            temporaryArtwork.updateContent(rq.content)
        } ?: createTemporaryArtwork(memberContext, rq)

        return getAutoSaveResponseString()
    }

    private fun createTemporaryArtwork(memberContext: MemberContext, rq: AutoSaveTemporaryArtworkRq): TemporaryArtwork {
        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)

        val temporaryArtwork = TemporaryArtwork(
            content = rq.content,
            writer = member
        )
        return temporaryArtworkRepository.save(temporaryArtwork)
    }

    private fun getAutoSaveResponseString(): String {
        return DateUtils.formattedNow() + "에 자동 저장 되었습니다."
    }

}