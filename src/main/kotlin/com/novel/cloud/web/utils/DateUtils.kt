package com.novel.cloud.web.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


object DateUtils {

    fun now(): Date {
        return Date()
    }

    fun addTime(date: Date, millisecond: Long): Date {
        return Date(date.time + millisecond)
    }

    fun formattedNow(): String {
        val today = Date()
        val pattern = "yyyy/MM/dd HH:mm:ss"
        val formatter = SimpleDateFormat(pattern)
        return formatter.format(today)
    }

    fun formatDateYYYYMMDD(createdDate: LocalDateTime?): String? {
        return createdDate?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd."))
    }

    fun formatDateComment(createdDate: LocalDateTime): String {
        val now = LocalDateTime.now()
        val oneDayAgo = now.minusDays(1)
        val twoDayAgo = now.minusDays(2)
        val threeDayAgo = now.minusDays(3)
        val oneWeekAgo = now.minus(1, ChronoUnit.WEEKS)
        val oneYearAgo = now.minus(1, ChronoUnit.YEARS)

        return when (createdDate) {
            in oneDayAgo..now -> createdDate.format(DateTimeFormatter.ofPattern("a hh:mm"))
            in twoDayAgo..oneDayAgo -> createdDate.format(DateTimeFormatter.ofPattern("하루 전"))
            in threeDayAgo..twoDayAgo -> createdDate.format(DateTimeFormatter.ofPattern("2일 전"))
            in oneWeekAgo..threeDayAgo -> createdDate.format(DateTimeFormatter.ofPattern("3일 전"))
            in oneYearAgo..oneWeekAgo -> createdDate.format(DateTimeFormatter.ofPattern("M월 d일"))
            else -> createdDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
        }
    }

}