package com.novel.cloud.web.domain.file.repository

import com.novel.cloud.db.entity.attach_file.AttachFile
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository: JpaRepository<AttachFile, Long> { }