package com.example.duong.trackmysleepquality.screenquanlity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO
import com.example.duong.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

class SleepQuanlityViewModel(private val sleepNightKey : Long = 0L,
                             val databaseDAO : SleepDatabaseDAO) :ViewModel(){
    private val viewModelJob = Job();
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // biến dùng cho việc điều hướng về.
    private val _navigationToSleepTracker = MutableLiveData<Boolean>()
    val navigationToSleepTracker : LiveData<Boolean>
        get() = _navigationToSleepTracker

    fun donNavigating() {
        _navigationToSleepTracker.value = null;
    }

    // đánh giá chất lượng giấc ngủ update vafo db, khi click vào icon ở view
    // thì sẽ thực hiện
    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = databaseDAO.get(sleepNightKey)?:return@withContext
                tonight.sleepQuality = quality;
                databaseDAO.update(tonight)
            }
        //canhr baó observer và kích hoạt điều hướng trở lại
            _navigationToSleepTracker.value = true;
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}