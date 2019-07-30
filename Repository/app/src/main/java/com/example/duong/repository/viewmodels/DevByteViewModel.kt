package com.example.duong.repository.viewmodels

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import android.widget.Toast
import com.example.duong.repository.database.VideosDatabase
import com.example.duong.repository.model.DevByteVideo
import com.example.duong.repository.network.DevByteNetWork
import com.example.duong.repository.network.asDomainModel
import com.example.duong.repository.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

class DevByteViewModel(application: Application) : AndroidViewModel(application){

    // create object repository và truyền database object vào, application ở trong đối số của viewmodel
    private val videosRepository = VideosRepository(VideosDatabase.getIntance(application))
    // create object job
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main
            + viewModelJob);
    // khởi tạo luôn
/*    private val _playlist = MutableLiveData<List<DevByteVideo>>()
    val playlist : LiveData<List<DevByteVideo>>
        get() {
            return _playlist;
        }*/


    val playlist = videosRepository.videos
    // sự kiện kích hoạt khi lỗi mạng
    private var _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    // flag để hiển thị thông báo lỗi
    private var _isNetworkErrorShown = MutableLiveData<Boolean>()
    val isNetworkErrorShown : LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        // khỏi tạo trong viewmodel luôn và cập nhật liên tục thay đỏi dữ liệu
        refreshDataFromRepository()
    }

    // khi khởi tạo sẽ lấy list và set các biến
    // rename thanfh refresh data from repository, khởi tạo trong init thì k cần key word sufend fun
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                // giờ sẽ load data từ from repository
                videosRepository.refreshVideos()

                // lấy ra playlist từ internet, JSON bao ngoài Array nên sử dụng một class
                // và trong hàm tạo của class là list model
               // --->val playlist = DevByteNetWork.devBytes.getPlaylist().await()

                //  Log.i("Duong",playlist.videos.toString())
                // gửi dữ liệu lên luồng chính, chuyển đổi list sang list của model
               // --->_playlist.postValue(playlist.asDomainModel())
                //Log.i("T",playlist.asDomainModel().toString());
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {

                // Show a Toast error message and hide the progress bar.
                if (playlist.value!!.isEmpty()) {
                    _eventNetworkError.value = true
                }

            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
               @Suppress("UNCHECKED_CAST")
               return DevByteViewModel(application) as T
           }
            throw IllegalArgumentException("Unknown contructor ViewModel!");
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel();
    }
}

