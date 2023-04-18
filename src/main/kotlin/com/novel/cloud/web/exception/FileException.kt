package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class FileException (
    message: String
) : GeneralHttpException(HttpStatus.BAD_REQUEST, message, null)