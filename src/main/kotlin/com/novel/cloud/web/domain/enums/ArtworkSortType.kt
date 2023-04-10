package com.novel.cloud.web.domain.enums

enum class ArtworkSortType(
    val type: String,
) {
    UPLOAD_DATE("업로드 날짜"),
    VIEWS("조회수"),
    LIKES("좋아요 수"),
    COMMENTS("댓글 수"),
}