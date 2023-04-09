package com.novel.cloud.web.domain.tag.controller

import com.novel.cloud.web.domain.tag.controller.rs.FindPopularTagRs
import com.novel.cloud.web.domain.tag.service.FindArtworkTagService
import com.novel.cloud.web.endpoint.ListResponse
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "태그")
@RestController
class ArtworkTagController (
    val findArtworkTagService: FindArtworkTagService
){

//    @Operation(summary = "인기 있는 해시태그 불러오기")
//    @GetMapping(ApiPath.TAG)
//    fun findPopularTag(): ListResponse<FindPopularTagRs> {
//        return findArtworkTagService.findPopularTags()
//    }


}