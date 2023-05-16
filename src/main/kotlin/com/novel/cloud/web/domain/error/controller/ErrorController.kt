package com.novel.cloud.web.domain.error.controller

import com.novel.cloud.web.exception.JwtException
import com.novel.cloud.web.path.ApiPath
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
class ErrorController {

    @RequestMapping(
        value = [ApiPath.ERROR_AUTH],
        method = [RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST]
    )
    fun errorAuth(@RequestParam(value = "message") message: String) {
        throw JwtException(message)
    }

}