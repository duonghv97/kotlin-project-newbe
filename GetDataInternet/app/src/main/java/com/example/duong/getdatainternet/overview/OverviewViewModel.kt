package com.example.duong.getdatainternet.overview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.duong.getdatainternet.network.MarsApi
import com.example.duong.getdatainternet.network.MarsApiFilter
import com.example.duong.getdatainternet.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
// status request image data từ internet
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    // định nghĩa coroutines job và scope
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    // create list đối tượng có các property giống 1 JSON object
    private val _properties = MutableLiveData<List<MarsProperty>>();
    val properties : LiveData<List<MarsProperty>>
        get() {
            return _properties
        }

    init {
        // khi call ở init sẽ show all
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }
    // call server và nhận dữ liệu
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) { // add filter để request
        // call web serivce
        // sử dụng enqueue trên Call object để start request trên background thread
/*        MarsApi.retrofitService.getProperties().enqueue(
            object: Callback<List<MarsProperty>> {
                // nhận response
                // khi bị false thì thông báo lỗi
                override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
                // khi nhận được response thì thông báo

                override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                    _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
                }
            })*/

        // call service
        var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
        // phóng vào Main thread, nếu phòng vào IO thread thì mới sử dụng withContext
        // vẫn chạy trên Main thread nhưng Coroutine cũng quản lý đồng thời
        // trả về kết quả từ network giá trị đã lấy đủ.
        coroutineScope.launch {
            try {
                // sử dụng status LiveData để biết được khi request đang ở state nào
                _status.value = MarsApiStatus.LOADING
                // thực thi,sẽ đợi để lấy xong dữ liệu
                var listResult = getPropertiesDeferred.await()
                // wait xong thì là load done
                _status.value = MarsApiStatus.DONE
                // gán dữ liệu từ list vào
                _properties.value = listResult // lấy được dữ liệu xong truyền vào LiveData
            } catch (e: Exception) {
                // nếu có lỗi thì gán error và list trống
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList(); // không có thì khởi tạo list rỗng
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // update filter thì chỉ cần truyền vào hàm filter là được
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }


    // create LiveData truyền object từ fragment này sang fragment khác, sử dụng điều hướng luôn
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty : LiveData<MarsProperty>
        get() = _navigateToSelectedProperty;


    // set dữ liệu vào biến
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty;
    }

    // khi thực hiện xong thif set bang null
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null;
    }

}