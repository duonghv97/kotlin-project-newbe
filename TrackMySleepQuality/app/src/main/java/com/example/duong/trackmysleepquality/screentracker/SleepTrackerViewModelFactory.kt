package com.example.duong.trackmysleepquality.screentracker

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO
import java.lang.IllegalArgumentException

// dùng để tạo object ViewModel
class SleepTrackerViewModelFactory(private val dataSource:SleepDatabaseDAO,
                                   private val application: Application) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(dataSource,application)as T
        }
        throw IllegalArgumentException("Enknown ViewModel Class!");
    }
}