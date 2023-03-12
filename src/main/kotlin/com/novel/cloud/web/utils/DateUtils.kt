package com.novel.cloud.web.utils

import lombok.experimental.UtilityClass
import java.util.*
@UtilityClass
class DateUtils {
    fun now(): Date? {
        return Date()
    }

    fun addTime(date: Date, millisecond: Long): Date? {
        return Date(date.getTime() + millisecond)
    }

}