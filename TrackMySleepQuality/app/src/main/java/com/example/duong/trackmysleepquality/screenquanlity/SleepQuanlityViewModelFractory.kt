package com.example.duong.trackmysleepquality.screenquanlity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO

class SleepQuanlityViewModelFractory(private val sleepNightKey:Long,
                                     private val dataSource:SleepDatabaseDAO) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepQuanlityViewModel::class.java)) {
            return SleepQuanlityViewModel(sleepNightKey,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class");
    }

}