package com.novel.cloud.web.domain.abbreviation.controller

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.domain.abbreviation.controller.rq.CreateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.DeleteAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.UpdateAbbreviationRq
import com.novel.cloud.web.domain.abbreviation.controller.rq.UpdateAbbreviationSequenceRq
import com.novel.cloud.web.domain.abbreviation.controller.rs.FindSequenceAbbreviationRs
import com.novel.cloud.web.domain.abbreviation.service.AbbreviationService
import com.novel.cloud.web.domain.abbreviation.service.FindAbbreviationService
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        abbreviationService.deleteAbbreviation(memberContext, rq)
    }
    @Operation(summary = "단축어 수정")
    @PutMapping(ApiPath.SHORTCUT)
    fun updateAbbreviation(@AuthenticationPrincipal memberContext: MemberContext,
                      @Validated @RequestBody rq: UpdateAbbreviationRq) {
        abbreviationService.updateAbbreviation(memberContext, rq)
    }

    @Operation(summary = "단축어 순서변경")
    @PutMapping(ApiPath.SHORTCUT_SEQUENCE)
    fun updateAbbreviationSequence(@AuthenticationPrincipal memberContext: MemberContext,
                 @Validated @RequestBody rq: UpdateAbbreviationSequenceRq) {
        abbreviationService.updateAbbreviationSequence(memberContext, rq)
    }

    @Operation(summary = "내 단축어 불러오기")
    @GetMapping(ApiPath.SHORTCUT)
    fun findAbbreviationSelf(@AuthenticationPrincipal memberContext: MemberContext): List<FindSequenceAbbreviationRs> {
        return findAbbreviationService.findAbbreviationSelf(memberContext)
    }

}