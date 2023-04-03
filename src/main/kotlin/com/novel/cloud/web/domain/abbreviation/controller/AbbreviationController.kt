package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rq.CreateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.DeleteAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.service.AbbreviationService
import com.novel.cloud.web.domain.abbreviation.service.FindAbbreviationService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Tag(name="단축어")
@RestController
class AbbreviationController(
    private val abbreviationService: AbbreviationService,
    private val findAbbreviationService: FindAbbreviationService
) {
    @Operation(summary = "단축어 생성")
    @PostMapping(ApiPath.SHORTCUT)
    fun createAbbreviation(@AuthenticationPrincipal memberContext: MemberContext,
                        @Validated @RequestBody rq: CreateAbbreviationRq) {
        abbreviationService.createAbbreviation(memberContext, rq)
    }

    @Operation(summary = "단축어 삭제")
    @DeleteMapping(ApiPath.SHORTCUT)
    fun deleteAbbreviation(@AuthenticationPrincipal memberContext: MemberContext,
                      @Validated @RequestBody rq: DeleteAbbreviationRq) {
        abbreviationService.deleteAbbreviation(memberContext, rq);
    }
//
//    @Operation(summary = "단축어 수정")
//    @PutMapping(ApiPath.SHORTCUT)
//    fun sArtwork(@AuthenticationPrincipal memberContext: MemberContext,
//                      @Validated @RequestPart(value = "rq") rq: CreateArtworkRq,
//                      @RequestPart(value = "files") files: List<MultipartFile>,
//                      @RequestPart(value = "thumbnail") thumbnail: MultipartFile): null {
//        return null
//    }
//
//    @Operation(summary = "단축어 순서변경")
//    @PostMapping(ApiPath.SHORTCUT)
//    fun seqence(@AuthenticationPrincipal memberContext: MemberContext,
//                 @Validated @RequestPart(value = "rq") rq: CreateArtworkRq,
//                 @RequestPart(value = "files") files: List<MultipartFile>,
//                 @RequestPart(value = "thumbnail") thumbnail: MultipartFile): null {
//        return null
//    }
//
//    @Operation(summary = "내 단축어 불러오기")
//    @GetMapping(ApiPath.SHORTCUT)
//    fun my(@AuthenticationPrincipal memberContext: MemberContext,
//                @Validated @RequestPart(value = "rq") rq: CreateArtworkRq,
//                @RequestPart(value = "files") files: List<MultipartFile>,
//                @RequestPart(value = "thumbnail") thumbnail: MultipartFile): null {
//        return null
//    }

}