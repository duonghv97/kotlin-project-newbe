package com.example.duong.getdatainternet

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.duong.getdatainternet.network.MarsProperty
import com.example.duong.getdatainternet.overview.MarsApiStatus
import com.example.duong.getdatainternet.overview.PhotoGridAdapter

// string url có thể null
// convert dữ liệu từ string sang uri rồi hiển thị lên imageview
// trong các method chuyển đổi dữ liệu lên view sẽ có 2 param: 1 là Views, 2 là data
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView,imgUrl:String?) {
    imgUrl?.let {
        // convert string url sang Uri object, để sử dụng HTTPs chema thì thêm buildUpon
        val imgUri = Uri.parse(imgUrl)
        // load image từ Uri object lên imgView
        // với context là ImageView, sau đó load Uri object hiển thị trên ImageView
//        Log.i("URI",imgUri.toString());

        // Khi ta load Uri object nếu mà đang loading thì ta sử dụng placeholder image loading
        // còn lỗi thì sử dụng image lỗi
        /*đang load thì mới hiển thị icon trong placeholder*/
        Glide.with(imgView.context)
            .load(imgUri) // load uri và into lên image
            .apply(RequestOptions() // khi add option sử dụng apply
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

// cập nhật những item thay đổi chứ k phải load lại cả list
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

// ràng buộc để hiển thị theo status.
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MarsApiStatus?) {
    when (status) {
        // đang load thì hiển thị icon loading
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        // đang error thì hiển thị icon connection_error
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        // done rồi thì không hiện icons
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}