package com.novel.cloud.web.domain.abbreviation.repository

import com.novel.cloud.db.entity.abbreviation.Abbreviation
import org.springframework.data.jpa.repository.JpaRepository

interface AbbreviationRepository: JpaRepository<Abbreviation, Long> {}