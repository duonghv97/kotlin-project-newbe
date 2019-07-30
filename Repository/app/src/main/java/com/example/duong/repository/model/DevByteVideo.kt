package com.example.duong.repository.model

import com.example.duong.repository.util.smartTruncate

// đây là domian object
// model có các thuộc tính tương ứng với JSON object
data class DevByteVideo(
    val title:String, // tiêu đề của video
    val description :String, // mô tả video
    val url:String, // đường dẫn
    val updated:String, // ngày update
    val thumbnail:String) { // ảnh nhỏ
    //short description hiển thị mô tả ngắn gọn
    val shortDescription : String
        // call hàm smartTruncate định nghĩa ở Util để hiển thị một phần description
        get() = description.smartTruncate(200)
    // ta có thể định nghĩa thuộc tính trong data class biến đổi thuộc tính ở hàm tạo
}