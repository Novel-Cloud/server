package com.novel.cloud.web.domain.member.repository

import com.novel.cloud.db.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?;

}