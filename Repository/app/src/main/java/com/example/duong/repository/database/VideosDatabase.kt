package com.example.duong.repository.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.room.Room.databaseBuilder as databaseBuilder1

// create database
@Database(entities = [Video::class], version = 1 ,exportSchema = false)
abstract class VideosDatabase: RoomDatabase() {
    abstract val videoDAO : VideoDAO
    // create object database singleton
    companion object {
        @Volatile
        private var INSTANCE : VideosDatabase? = null

        fun getIntance(context: Context): VideosDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VideosDatabase::class.java,
                        "video_android" )
                        .fallbackToDestructiveMigration() // tránh mất dữ liệu
                        .build()
                    INSTANCE = instance;
                }
                return instance;
            }
        }
    }
}