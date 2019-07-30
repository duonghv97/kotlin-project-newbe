package com.example.duong.trackmysleepquality.screendetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO

class SleepDetailViewModelFactory(private val sleepNightKey:Long,
                                  private val databaseDAO: SleepDatabaseDAO): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightKey,databaseDAO) as T
        }
        throw IllegalArgumentException("Unknows ViewModel class Exception!");
    }
}