package com.novel.cloud.web.utils

import java.util.Date

object DateUtils {

    fun now(): Date {
        return Date()
    }

    fun addTime(date: Date, millisecond: Long): Date {
        return Date(date.time + millisecond)
    }

}