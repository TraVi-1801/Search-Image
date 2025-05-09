package com.vic_project.search_image.utils.time

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object TimeUtils {
    private const val defaultPlaceHolder = "-"
    private const val defaultRegex = "dd/MM/yyyy"

    private fun convertMillisecondToDate(millisecond: Long, regex: String): String {
        val c = Calendar.getInstance(Locale("vi", "VN"))
        c.timeInMillis = millisecond
        val format = SimpleDateFormat(regex, Locale("vi", "VN"))
        format.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        return format.format(c.time)
    }

    fun Long?.toDate(
        regex: String = defaultRegex,
        placeHolder: String = defaultPlaceHolder
    ): String {
        this ?: return placeHolder
        return convertMillisecondToDate(this, regex)
    }

    fun Long?.getTimeAgo(
        placeHolder: String = defaultPlaceHolder
    ): String {
        this ?: return placeHolder
        val now = System.currentTimeMillis()

        if (this > now || this <= 0) {
            return "just now"
        }

        val diff = now - this

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "vừa đăng"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} phút truước"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} giờ trước"
            diff < TimeUnit.DAYS.toMillis(30) -> "${TimeUnit.MILLISECONDS.toDays(diff)} ngày trước"
            diff < TimeUnit.DAYS.toMillis(365) -> "${diff / TimeUnit.DAYS.toMillis(30)} tháng trước"
            else -> "${diff / TimeUnit.DAYS.toMillis(365)} năm trước"
        }
    }

    fun LocalDateTime?.toTimeLong() : Long? {
        this ?: return null
        return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun currentTime() : Long {
        return System.currentTimeMillis()
    }

    fun addMinutes(time: Long?, minutes: Int?): Long? {
        if (time == null) return null
        if (minutes == null) return time
        val addedTime = minutes * 60 * 1000
        return time + addedTime
    }

    fun Long?.displayText(): String {
        if (this == null) return defaultPlaceHolder
        val dayOfWeek = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.NARROW, Locale("vi", "VN")).replace(".", "")
    }
    fun timeCurrent() : Long {
        return Calendar.getInstance().timeInMillis
    }
    fun timeFlow(intervalMillis: Long,date: LocalDate): Flow<Long> = flow {
        while (true) {
            val currentTime = System.currentTimeMillis()
            val currentZonedDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(currentTime),
                ZoneId.systemDefault()
            )

            val combinedDateTime = ZonedDateTime.of(
                date,
                LocalTime.of(
                    currentZonedDateTime.hour,
                    currentZonedDateTime.minute,
                    currentZonedDateTime.second,
                    currentZonedDateTime.nano
                ),
                ZoneId.systemDefault()
            )

            val combinedMillis = combinedDateTime.toInstant().toEpochMilli()

            emit(combinedMillis)
            delay(intervalMillis)
        }
    }

    fun generateTimeMillisList(date: LocalDate): List<Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.monthValue - 1) // Calendar.MONTH is zero-based
            set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val timeList = mutableListOf<Long>()

        for (i in 0 until 48) { // 48 khoảng 30 phút trong ngày
            timeList.add(calendar.timeInMillis)
            calendar.add(Calendar.MINUTE, 30)
        }

        return timeList
    }

    fun findNearestIndex(timeList: List<Long>, currentTimeMillis: Long): Int {
        return timeList.indexOf(timeList.minByOrNull { kotlin.math.abs(it - currentTimeMillis) } ?: timeList.first())
    }

    fun generateDateList(fromDate: Long?, toDate: Long?): List<Long> {
        if (fromDate == null || toDate == null) return emptyList()
        val dayInMillis = TimeUnit.DAYS.toMillis(1)
        val dateList = mutableListOf<Long>()

        val calendar = Calendar.getInstance().apply {
            timeInMillis = fromDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        var currentDate = calendar.timeInMillis

        while (currentDate <= toDate) {
            dateList.add(currentDate)
            currentDate += dayInMillis
        }

        return dateList
    }

    fun LocalDate?.getTimeNote(): Long? {
        if (this == null) return null

        val now = LocalTime.now()
        val roundedTime = when {
            now.minute >= 30 -> LocalTime.of(now.hour + 1, 0)
            else -> LocalTime.of(now.hour, 30)
        }
        val roundedDateTime = LocalDateTime.of(this, roundedTime)
        return roundedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun LocalDate.startOfDay(): Long {
        return this.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun LocalDate.endOfDay(): Long {
        return this.atTime(23, 59, 59, 999_999_999) // 23:59:59.999
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun LocalDate.getFirstDayMinus6(): LocalDate {
        return this.withDayOfMonth(1).minusDays(6)
    }

    fun LocalDate.getLastDayPlus6(): LocalDate {
        return this.withDayOfMonth(this.lengthOfMonth()).plusDays(6)
    }

    fun Long?.longToYearMonth(): YearMonth {
        if (this == null) return YearMonth.now()
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .let { YearMonth.from(it) }
    }

    fun Long?.longToLocalTime(): LocalTime {
        if (this == null) return LocalTime.now()

        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
    }

    fun Long?.longToLocalDate(): LocalDate {
        if (this == null) return LocalDate.now()
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun Long?.longToWeek(): List<LocalDate> {
        val localDate = this?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        } ?: LocalDate.now()

        val startOfWeek = localDate.with(DayOfWeek.MONDAY)
        return (0..6).map { startOfWeek.plusDays(it.toLong()) }
    }

    fun localDateTimeToLong(date: LocalDate, time: LocalTime): Long {
        return LocalDateTime.of(date, time)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}

enum class DateFormat(val pattern: String) {
    HH_MM("HH:mm"),
    DD_MM("dd/MM"),
    DD_MM_YYYY("dd/MM/yyyy"),
    DD_MM_YYYY_HH_MM("dd/MM/yyyy HH:mm"),
    HH_MM_DD_MM_YYYY("c")
}