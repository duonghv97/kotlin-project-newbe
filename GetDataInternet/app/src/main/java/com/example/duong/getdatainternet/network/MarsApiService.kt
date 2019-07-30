package com.example.duong.getdatainternet.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// service
private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

// create Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
// create Retrofit object để converter data
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // truyền moshi object vào
    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // cho phép return một lớp khác không phải Call class mặc định
    .baseUrl(BASE_URL)
    .build()

// định nghĩa service, request and response JSON từ server
interface MarsApiService {
    // get JSON response từ web service chỉ cần sử dụng getProperties
    // thêm anotation thể
    // Call object dùng để start request
    @GET("realestate")
    fun getProperties(@Query("filter") type: String):   // filter theo type khi response về
            Deferred<List<MarsProperty>> // dữ liệu sẽ kiểu List<MarsProperty> chứ k phải string nữa
    // sẽ thay thế Call object thành DeffedObject
}
// object là cách định nghĩa singleton trong kotlin
// khởi tạo service và cho retrofit vào để tạo singleton retrofit object
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java) }
}
// create enum các giá trị của option menu sẽ đưa vào url_base để filter
// khi muốn set giá trị cho các đối tượng trong enum thì sử dụng hàm tạo truyền param vào
enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

