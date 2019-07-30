package com.example.duong.repository.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.example.duong.repository.database.VideosDatabase
import com.example.duong.repository.database.asDomainModel
import com.example.duong.repository.model.DevByteVideo
import com.example.duong.repository.network.DevByteNetWork
import com.example.duong.repository.network.asDatabaseModel
import com.example.duong.repository.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

// repository sẽ lấy dữ liệu từ trên internet và lưu trữ trên ổ đĩa.
// repository sẽ database object để thao tác với các method DAO
class VideosRepository (private val database: VideosDatabase){

    // lấy dữ liệu từ database convert domain model và lưu thành LiveData
    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDAO.getVideos()) {
        it.asDomainModel()
    }

    // lấy dữ liệu từ internet sau đó insert vào database
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh video is called")
            // get data from internet
            val playlist = DevByteNetWork.devBytes.getPlaylist().await();
            // convert data sang database model sau đó insert database
            database.videoDAO.insertAll(playlist.asDatabaseModel());
        }
    }
}