package com.novel.cloud.web.domain.abbreviation.service

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.novel.cloud.web.domain.abbreviation.repository.AbbreviationRepository
import com.novel.cloud.web.exception.NotFoundAbbreviationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindAbbreviationService (
    private val abbreviationRepository: AbbreviationRepository
) {
    fun findByIdOrElseThrow(abbreviationId: Long): Abbreviation {
        return abbreviationRepository.findById(abbreviationId)
            .orElseThrow { NotFoundAbbreviationException() }
    }

}