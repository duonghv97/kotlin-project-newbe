package com.example.duong.trackmysleepquality.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

// đối với 1 đêm ngủ sẽ lưu time bắt đầu, time kết thúc và chất lượng giấc ngủ
// để có thể tracker (theo dõi)
@Entity(tableName =  "daily_sleep_quality_table")
class SleepNight {
    // set là khóa chính
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L // đuôi L thể hiện kiểu Long

    // chú ý biến sau sẽ được cập nhật dữ liệu vào thì ta để biến là var
    @ColumnInfo(name = "start_time_milli")
    var startTimeMilli: Long = System.currentTimeMillis()

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli // thời gian kết thúc khởi tạo bằng thời gian hiện tại để nói chưa có
    // khởi tạo bằng -1 tức là chưa có đánh giá nào cả
    @ColumnInfo(name = "quality_rating")
    var sleepQuality: Int = -1
}
