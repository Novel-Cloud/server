package com.novel.cloud.web.domain.enums

enum class UploadDateType(
    val type: String,
) {
    AN_HOUR_AGO("1시간 전"),
    TODAY("오늘"),
    THIS_WEEK("이번주"),
    THIS_MONTH("이번달"),
    THIS_YEAR("올해")
}