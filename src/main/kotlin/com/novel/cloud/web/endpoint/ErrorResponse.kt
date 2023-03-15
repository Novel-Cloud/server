package com.novel.cloud.web.endpoint

data class ErrorResponse (
    val statusCode: Int? = null,
    val reason: String? = null,
    val message: String? = null
)