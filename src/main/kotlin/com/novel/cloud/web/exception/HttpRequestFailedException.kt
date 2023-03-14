package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus
class HttpRequestFailedException (
    httpStatus: HttpStatus,
    message: String
) : GeneralHttpException(httpStatus, message, null)
