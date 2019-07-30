package com.example.duong.repository.network

import com.example.duong.repository.model.DevByteVideo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/";
// định nghĩa service
interface DevByteService {
    // value kia thì get trên server phải có cái service đó
    @GET("devbytes")
    fun getPlaylist() : Deferred<NetworkVideoContainer>
    // kiểu trả về của function là List các Object Video
}
// server này sẽ chứa dữ liệu của các video
object DevByteNetWork {
    // create retrofit object
    // url là một server back-end
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    // khởi tạo service
    val devBytes = retrofit.create(DevByteService::class.java)
}