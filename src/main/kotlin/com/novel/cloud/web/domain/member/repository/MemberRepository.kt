package com.novel.cloud.web.domain.member.repository

import com.novel.cloud.db.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByEmail(email: String): Optional<Member>

}