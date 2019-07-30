package com.example.duong.trackmysleepquality.screendetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import android.util.Log
import android.widget.Toast
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO
import com.example.duong.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

// file này chạy thì sẽ hiển thị dữ liệu nên luôn rồi
class SleepDetailViewModel(private val sleepNightKey:Long,
                            databaseDAO: SleepDatabaseDAO) : ViewModel(){
    private val viewModelJob = Job();
    private val uiScope = CoroutineScope(Dispatchers.Main +
            viewModelJob);
    // bien de dieu huong
    private val _navigateSleepTracker = MutableLiveData<Boolean?>()
    val navigateSleepTracker : LiveData<Boolean?>
        get() {
            return _navigateSleepTracker;
        }
    val database = databaseDAO;
    private val night: LiveData<SleepNight>
    fun getNight() = night

    // khi khởi tạo có luôn dữ liệu của night từ nightKey
    init {
        // key có
        night=database.getNightById(sleepNightKey)

    }
    fun donNavigating() {
        _navigateSleepTracker.value = null;
    }
    // khi click close sẽ set biến điều hướng thành true và livedata sẽ
    // observer điều hướng luôn
    fun onClickClose() {
        _navigateSleepTracker.value = true;

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}