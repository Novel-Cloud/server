package com.novel.cloud.web.domain.abbreviation.repository

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import java.util.Optional

interface AbbreviationRepositoryCustom {
    fun findMyLastSequenceAbbreviation(memberId: Long?): Optional<Abbreviation>
    fun findSequenceAbbreviation(memberId: Long?): List<Abbreviation>
}