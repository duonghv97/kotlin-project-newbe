package com.example.duong.trackmysleepquality

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.support.v4.text.HtmlCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import com.example.duong.trackmysleepquality.database.SleepNight
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// convert 1 phút ra second
private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
//convert 1 h ra second, sử dụng thư viện TimeUnit
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
// convert data
fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    // lấy ra ngày hiện tại
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return when {
        // khi thông báo sẽ convert từ second sang giờ, phút tương ứng
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }
}

// định nghĩa các method hỗ trợ ở Util không cần cho vào class nào cả
    fun convertNumericQualityToString(quality: Int, resources: Resources): String {
        var qualityString = resources.getString(R.string.three_ok)
        when (quality) {
            -1 -> qualityString = "--"
            0 -> qualityString = resources.getString(R.string.zero_very_bad)
            1 -> qualityString = resources.getString(R.string.one_poor)
            2 -> qualityString = resources.getString(R.string.two_soso)
            4 -> qualityString = resources.getString(R.string.four_pretty_good)
            5 -> qualityString = resources.getString(R.string.five_excellent)
        }
        return qualityString
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
    }

    fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
        val sb = StringBuilder()
        sb.apply {
            append(resources.getString(R.string.title))
            nights.forEach {
                append("<br>")
                append(resources.getString(R.string.start_time))
                append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
                if (it.endTimeMilli != it.startTimeMilli) {
                    append(resources.getString(R.string.end_time))
                    append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                    append(resources.getString(R.string.quality))
                    append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
                    append(resources.getString(R.string.hours_slept))
                    // Hours
                    append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
                    // Minutes
                    append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
                    // Seconds
                    append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
    // trong recyclerview đã có viewholder ta extends lại và call method thôi
    class TextItemViewHolder (val textView :TextView) : RecyclerView.ViewHolder(textView)

