package com.novel.cloud.web.exception

import org.springframework.http.HttpStatus

class NotMatchedParentChildArtworkIdException
    : GeneralHttpException(HttpStatus.BAD_REQUEST, "부모 댓글과 자식 댓글이 참조하고 있는 작품이 다릅니다.", null)