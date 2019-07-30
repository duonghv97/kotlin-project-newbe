package com.example.duong.trackmysleepquality.screentracker

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.duong.trackmysleepquality.R
import com.example.duong.trackmysleepquality.convertDurationToFormatted
import com.example.duong.trackmysleepquality.convertNumericQualityToString
import com.example.duong.trackmysleepquality.database.SleepNight


// file Util sẽ chỉ toàn function
// ta định nghĩa cho từng views cần hiển thị dữ liệu lên
/*annotation này sẽ được truyền vào file xml*/
@BindingAdapter("sleepDurationFormatted")
// ta định nghĩa một extendtion function của TextView
// setup có thể null để loại exception IIegalArgumentException not-null
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
    item?.let {
        // covert string sang text hiển thị
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }

}

// Khi muốn biến đổi dữ liệu ta có thể dùng Tranformation nhưng bindingAdapter hỗ trợ
@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}
@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?) {
    item?.let {
        // display image
        setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2

            3 -> R.drawable.ic_sleep_3

            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }

}

