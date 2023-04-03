package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class AbbreviationSequenceContainException
    : GeneralHttpException(HttpStatus.BAD_REQUEST, "기존 단축어 id가 일치하지 않아 단축어 순서를 변경할 수 없습니다.", null)