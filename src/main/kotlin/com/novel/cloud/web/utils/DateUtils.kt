package com.novel.cloud.web.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    fun now(): Date {
        return Date()
    }

    fun addTime(date: Date, millisecond: Long): Date {
        return Date(date.time + millisecond)
    }

    fun formatedNow(): String {
        val today = Date()
        val pattern = "yyyy/MM/dd HH:mm:ss"
        val formatter = SimpleDateFormat(pattern)
        return formatter.format(today)
    }

}