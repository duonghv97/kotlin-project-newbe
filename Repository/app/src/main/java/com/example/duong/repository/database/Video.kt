package com.example.duong.repository.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.duong.repository.model.DevByteVideo
/* đây là định nghĩa entities cho Room*/
@Entity(tableName = "database_video")
// set url là primary key thôi
data class Video constructor(
    @PrimaryKey
    val url:String,
    val updated:String,
    val title:String,
    val description:String,
    val thumbnail:String )


// create extention function convert database objects into domain objects
// ta sẽ tạo extention cho List database để convert nó sang List domain để hiển thị
fun List<Video>.asDomainModel() : List<DevByteVideo> {
    // do thằng mở rộng extention ra nó là list rồi nên return map luôn cho từng item
    return map {
        DevByteVideo(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }
}