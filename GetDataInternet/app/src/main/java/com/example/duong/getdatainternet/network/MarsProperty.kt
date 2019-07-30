package com.example.duong.getdatainternet.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// serialiable
@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double) : Parcelable {
    // trả về true nếu type = "rent"
    val isRental
        get() = type == "rent"
}