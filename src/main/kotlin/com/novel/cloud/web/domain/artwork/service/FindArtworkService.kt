package com.novel.cloud.web.domain.artwork.service

import com.novel.cloud.web.config.security.context.MemberContext
import com.novel.cloud.web.endpoint.Pagination
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class FindArtworkService {

    fun findAllArtwork(memberContext: MemberContext, pagination: Pagination) {

    }

}