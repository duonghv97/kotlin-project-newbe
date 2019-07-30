package com.example.duong.repository.util

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageview: ImageView,url:String ) {
    Glide.with(imageview.context)
        .load(url)
        .into(imageview)
}


// khi lỗi mạng và có playlist
@BindingAdapter("isNetworkError", "playlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, playlist: Any?) {
    // nếu khi mà playlist hiển thị ra thì ẩn snipper loading và ngược lại playlist rỗng thì ẩn
    view.visibility = if (playlist != null) View.GONE else View.VISIBLE

    // khi mà mạng bị lỗi thì ta cũng ẩn snipper_loading đi.
    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}