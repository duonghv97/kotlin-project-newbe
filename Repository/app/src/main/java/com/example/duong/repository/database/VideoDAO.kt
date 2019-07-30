package com.example.duong.repository.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface VideoDAO {
    @Query("SELECT * FROM database_video")
    fun getVideos() : LiveData<List<Video>>

    // từ là khi đã có dữ rội xảy ra xung đội thì sẽ thay thế
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos : List<Video>) // insert all video từ network into database
}