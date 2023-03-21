package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.db.entity.artwork.Artwork
import com.novel.cloud.db.entity.artwork.Tag
import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.repository.ArtworkRepository
import com.novel.cloud.web.domain.member.service.FindMemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArtworkService(
    private val findMemberService: FindMemberService,
    private val artworkRepository: ArtworkRepository,
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

//    fun createArtwork(memberContext: MemberContext) {
//        val member = findMemberService.findLoginMemberOrElseThrow(memberContext)
//        val artwork = Artwork
//    }

}