package com.novel.cloud.web.config.p6spy

import com.p6spy.engine.common.ConnectionInformation
import com.p6spy.engine.event.JdbcEventListener
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import java.sql.Date
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.*

@Configuration
class P6spyConfig : JdbcEventListener(), MessageFormattingStrategy {

    override fun onAfterGetConnection(connectionInformation: ConnectionInformation, e: SQLException?) {
        P6SpyOptions.getActiveInstance().logMessageFormat = javaClass.name
    }

    override fun formatMessage(
        connectionId: Int, now: String, elapsed: Long, category: String,
        prepared: String, sql: String, url: String
    ): String {
        val message = basicInfo(now, elapsed, category, connectionId, url)
        return formatSql(message, sql)
    }

    private fun formatSql(message: StringBuilder, sql: String): String {
        return if (!StringUtils.hasText(sql)) {
            message.toString()
        } else message.append(highlight(format(sql))).toString()
    }

    private fun highlight(sql: String): String {
        return FormatStyle.HIGHLIGHT.formatter.format(sql)
    }

    private fun format(sql: String): String {
        return if (isDDL(sql)) {
            FormatStyle.DDL.formatter.format(sql)
        } else FormatStyle.BASIC.formatter.format(sql)
    }

    private fun isDDL(sql: String): Boolean {
        return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment")
    }

    private fun basicInfo(
        now: String, elapsed: Long, category: String, connectionId: Int,
        url: String
    ): StringBuilder {
        return StringBuilder()
            .append(milliSecondToDateTime(now))
            .append(SEPARATOR)
            .append(String.format("Execution Time : %s ms", elapsed))
            .append(SEPARATOR)
            .append(String.format("Category : %s", category))
            .append(SEPARATOR)
            .append(String.format("Connection Id : %s", connectionId))
            .append(SEPARATOR)
            .append(url)
            .append(NEW_LINE)
    }

    private fun milliSecondToDateTime(now: String): String {
        val simpleDateFormat = SimpleDateFormat(
            DATE_TIME_FORMAT,
            Locale.KOREA
        )
        return simpleDateFormat.format(Date(now.toLong()))
    }

    companion object {
        private val NEW_LINE = System.lineSeparator()
        private const val SEPARATOR = " | "
        private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
    }
}