package com.novel.cloud.web.domain.artwork.controller

import com.novel.cloud.web.domain.artwork.service.ArtworkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController


@Tag(name="작품")
@RestController
class ArtworkController(
    private val artworkService: ArtworkService
) {

}