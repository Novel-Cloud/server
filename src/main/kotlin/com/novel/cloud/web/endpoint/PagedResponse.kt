package com.novel.cloud.web.endpoint

data class PagedResponse<T> (
    val pagination: Pagination? = null,
    val list: List<T>? = null
)