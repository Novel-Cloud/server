package com.novel.cloud.web.endpoint

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.data.domain.PageRequest

@Setter
data class Pagination(
    var page: Int = 1,
    var size: Int = 10,
    var totalCount: Long? = null,
    var totalPages: Int? = null
) {

    init {
        page = checkPage(page)
        size = checkSize(size)
    }

    fun toPageRequest(): PageRequest {
        return PageRequest.of(page - 1, size)
    }

    companion object {
        private fun checkSize(size: Int): Int {
            return when (size < 1 || size > 100) {
                true -> 10
                false -> size
            }
        }

        private fun checkPage(page: Int): Int {
            return when (page < 1) {
                true -> 1
                false -> page
            }
        }
    }

}