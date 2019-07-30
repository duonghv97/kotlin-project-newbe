package com.example.duong.repository.util

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

//  sử dụng String. tức là đầu vào là string và thêm số lượng length
fun String.smartTruncate(length: Int): String {
/*   solution:--> split cả string đó xong đó add dần đến khi nào đủ số lượng
    loại bỏ kí tự dấu ở cuối string nếu có và append thêm ...*/
    val words = split(" ");
    // split String đầu vào theo regax là " " sẽ ra list word
    var added = 0
    var hasMore = false
    // sử dụng string builder để nối chuỗi vì string nó bị đóng băng ô nhớ
    val builder = StringBuilder()
    for (word in words) {
        // lấy ra 200 word
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    /*     sử dụng list.map() sẽ tạo ra một list mới có các phần tử là kết quả của
     phần thực thi bên trong map*/

    PUNCTUATION.map {
        // loại bỏ dấu ở cuối string.
        if (builder.endsWith(it)) { // xét từng kí tự, nếu
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }
    // nếu quá 200 word thì nói thêm ... thể hiện đang còn
    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}