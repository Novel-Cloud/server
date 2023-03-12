package com.novel.cloud.web.exception

import lombok.Getter
import org.springframework.http.HttpStatus
@Getter
open class GeneralHttpException(
    private val httpStatus: HttpStatus?,
    override val message: String?,
    override val cause: Throwable?
) : RuntimeException(message, cause)