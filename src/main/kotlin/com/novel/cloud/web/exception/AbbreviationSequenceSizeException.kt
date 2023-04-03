package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class AbbreviationSequenceSizeException
    : GeneralHttpException(HttpStatus.BAD_REQUEST, "사이즈가 맞지 않아 단축어 순서를 변경할 수 없습니다.", null)