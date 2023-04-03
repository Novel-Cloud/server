package com.novel.cloud.web.domain.abbreviation.repository

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import com.querydsl.jpa.impl.JPAQueryFactory
import java.util.Optional

import com.novel.cloud.db.entity.abbreviation.QAbbreviation.abbreviation

class AbbreviationRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory
): AbbreviationRepositoryCustom {

    override fun findMyLastSequenceAbbreviation(memberId: Long?): Optional<Abbreviation> {
        return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(abbreviation)
                .where(abbreviation.writer.id.eq(memberId))
                .orderBy(abbreviation.sequence.desc())
                .fetchFirst()
        );
    }

}