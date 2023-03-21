package com.novel.cloud.web.domain.error.controller

import com.novel.cloud.web.exception.JwtException
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
class ErrorController {

    @GetMapping(ApiPath.ERROR_AUTH)
    fun errorAuth(@RequestParam(value = "message") message: String) {
        throw JwtException(message)
    }

}