package com.example.duong.repository.network

import android.arch.lifecycle.Transformations.map
import com.example.duong.repository.database.Video
import com.example.duong.repository.model.DevByteVideo
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
// đối tượng này có list các video
data class NetworkVideoContainer(val videos: List<NetworkVideo>)

// tạo ra jsonadapter sẽ transform từ JSON sang Kotlin, tạo ra class sẽ chứa list các
// JSON object nên kiểu cũng là JSONClass để nó chuyển đổi. Sau đó thì ta mới chuyển sang dữ liệu trong model
@JsonClass(generateAdapter = true)
data class NetworkVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?)


// khi call sẽ là một đối tượng có kiểu như vậy sẽ gọi fucntion đối tượng đó sẽ bị thay đổi
fun NetworkVideoContainer.asDomainModel(): List<DevByteVideo> {
    // duyệt từng phần tử trong videos và cho sang list model
    return videos.map {
        DevByteVideo(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
    // list mỗi phần tử là một model object DevByteVideo
    // convert kết quả từ network sang list ở domain object
}

// convert từ duwx lieeuj từ JSON về sang database object
fun NetworkVideoContainer.asDatabaseModel() : List<Video> {
    // do mở rộng là 1 class và trong class đó có list, nên ta trỏ vào list đó rồi mới thực hiện map
    // cho từng item
    return videos.map {
        Video(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail =  it.thumbnail )
    }
}