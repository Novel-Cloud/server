package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.artwork.controller.rq.AutoSaveTemporaryArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.CreateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.DeleteArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.UpdateArtworkRq
import com.novel.cloud.web.domain.artwork.controller.rq.UpdateArtworkViewRq
import com.novel.cloud.web.domain.artwork.service.ArtworkService
import com.novel.cloud.web.domain.file.service.FileService
import com.novel.cloud.web.path.ApiPath
import com.novel.cloud.web.utils.FileValidateUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@Tag(name = "작품")
@RestController
class ArtworkController(
    private val artworkService: ArtworkService,
    private val fileService: FileService
) {

    @Operation(summary = "작품 자동 저장")
    @PostMapping(ApiPath.ARTWORK_SAVE)
    fun autoSaveArtwork(
        @AuthenticationPrincipal memberContext: MemberContext,
        @Validated @RequestBody rq: AutoSaveTemporaryArtworkRq
    ): String {
        return artworkService.autoSaveArtwork(memberContext, rq)
    }

    @Operation(summary = "최종 작품 등록")
    @PostMapping(ApiPath.ARTWORK_SUBMIT)
    fun submitArtwork(
        @AuthenticationPrincipal memberContext: MemberContext,
        @Validated @RequestPart(value = "rq") rq: CreateArtworkRq,
        @RequestPart(value = "thumbnail", required = false) thumbnail: MultipartFile?,
        @RequestPart(value = "files", required = false) files: List<MultipartFile>?,
    ) {
        val artwork = artworkService.submitArtwork(memberContext, rq)
        // TODO::refactoring
        thumbnail?.let {
            files?.let {
                FileValidateUtils.imageValidationCheck(files)
                fileService.uploadArtworkImage(memberContext, artwork, thumbnail, files)
            }
        }
    }

    @Operation(summary = "작품 수정")
    @PutMapping(ApiPath.ARTWORK_UPDATE)
    fun updateArtwork(
        @AuthenticationPrincipal memberContext: MemberContext,
        @Validated @RequestPart(value = "rq") rq: UpdateArtworkRq,
        @RequestPart(value = "thumbnail") thumbnail: MultipartFile,
        @RequestPart(value = "files") files: List<MultipartFile>,
    ) {
        FileValidateUtils.imageValidationCheck(files)
        val artwork = artworkService.updateArtwork(memberContext, rq)
        fileService.updateArtworkImage(memberContext, artwork, thumbnail, files)
    }

    @Operation(summary = "작품 삭제")
    @DeleteMapping(ApiPath.ARTWORK_DELETE)
    fun deleteArtwork(
        @AuthenticationPrincipal memberContext: MemberContext,
        @Validated @RequestBody rq: DeleteArtworkRq
    ) {
        artworkService.deleteArtwork(memberContext, rq)
    }

    @Operation(summary = "조회수 증가")
    @PutMapping(ApiPath.VIEW_ARTWORK)
    fun updateArtworkView(@Validated @RequestBody rq: UpdateArtworkViewRq) {
        artworkService.updateArtworkView(rq)
    }

}