package com.vic_project.search_image.utils.time

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit

object Timer {

    //time to countdown - 1hr - 60secs
    const val END_TIME: Long = 0L
    const val DEFAULT_TIME_COUNTDOWN: Long = 180000L
    const val ONE_SECOND_INTERVAL = 1000L
    private const val TIME_FORMAT = "%02d:%02d"


    //convert time to milli seconds
    @SuppressLint("DefaultLocale")
    fun Long.formatToTimeSecond(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )


    @SuppressLint("DefaultLocale")
    fun Long.formatTime(): String = String.format(
        "%02d",
        TimeUnit.MILLISECONDS.toSeconds(this)
    )
}