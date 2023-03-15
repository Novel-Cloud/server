package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

open class GeneralHttpException(
    val httpStatus: HttpStatus,
    override val message: String,
    override val cause: Throwable?
): RuntimeException(message, cause)