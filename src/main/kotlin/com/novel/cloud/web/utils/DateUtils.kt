package com.novel.cloud.web.utils

import lombok.experimental.UtilityClass
import java.util.*
@UtilityClass
class DateUtils {

    companion object {
        fun now(): Date {
            return Date()
        }

        fun addTime(date: Date, millisecond: Long): Date {
            return Date(date.time + millisecond)
        }

    }

}