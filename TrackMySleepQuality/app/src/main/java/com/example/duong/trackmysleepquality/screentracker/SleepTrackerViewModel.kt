package com.example.duong.trackmysleepquality.screentracker

import android.app.Application
import android.arch.lifecycle.*
import android.provider.SyncStateContract.Helpers.insert
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO
import com.example.duong.trackmysleepquality.database.SleepNight
import com.example.duong.trackmysleepquality.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(val databaseDAO:SleepDatabaseDAO,
                            application: Application) : AndroidViewModel(application) {

    // lưu dữ liệu của night
    private val _navigateToSleepQuatity = MutableLiveData<SleepNight>()
    val navigateToSleepNight : LiveData<SleepNight>
        get() = _navigateToSleepQuatity

    fun doneNavigating() {
        _navigateToSleepQuatity.value = null;
    }
    // tạo đối tượng job để hủy coroutines
    private var viewModelJob = Job()
    // xác định scope cho coroutines
    // sẽ launch vào trong uiScope và chạy trên luồng chính
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    // sẽ là list record, cho nó là livedata để khi có sự thay đổi thì cập nhật luôn
    val nights = databaseDAO.getAllNights();

    // convert sang string để hiển thị lên textview
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }
    private var tonight = MutableLiveData<SleepNight?>()
    init {
        initializeTonight()
    }

    // trong uiScope sẽ phóng 1 coroutine
    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }
/*     do ta đang chạy trên luồng chính và muốn đẩy task nào đó sang thread khác
    thì tao tạo luôn vào phóng nó vào luôn và chỉ ra scope bao người nó */

    // trả ra 1 đêm hiện đang theo dõi
    private suspend fun getTonightFromDatabase(): SleepNight? {
        // trả về kết quả từ một coroutine sẽ chạy trên Dispatchers.IO context
        // Bởi vì lấy dữ liệu từ database là một I/O Operations
        // scope sẽ xác định run trên thread nào. và Dispatcher sẽ phóng coroutine vào thread đó
        // đoạn code này thể hiện chạy bất đồng bộ
        // context ẩn danh sẽ chứa thread cho coroutine chạy trên thread đó
        return withContext(Dispatchers.IO) {
            // lấy từ CSDL ra
            var night = databaseDAO.getTonight()
            // check xem giờ bắt đầu ngủ và lúc dậy bằng nhau thì việc follow đó là null
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        // scope sẽ phóng vào main thread
        uiScope.launch {
            val newNight = SleepNight()
            // sẽ tạo ra scope chứa thread IO để phóng coroutine vào đó thao tác với database
            insert(newNight)
            // phóng tiếp 1 coroutine vào dispatchers.IO thread,

/*             sở dĩ phải lấy night đang xét là vì, khi stop ta sẽ update lại cái tonight đó
            nên khi start cần lấy ra để khi stop còn gọi vào được và cập nhật.*/
            tonight.value = getTonightFromDatabase()
        }
    }
    private suspend fun insert(night: SleepNight) {
        // launch luôn vào Dispatchers.IO
        withContext(Dispatchers.IO) {
            databaseDAO.insert(night)
        }
    }

    // khi click button stop, thì kết thúc và nhảy sang Quality Fragment
    fun onStopTracking() {
        uiScope.launch {
            // tức là nếu không có value thì sẽ dừng launch luôn
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            // lấy thời gian hiện tại khi người đó click button end và cập nhật vào db
            update(oldNight)
            // cập nhật lại tonight cả thời gian và quality
            _navigateToSleepQuatity.value = oldNight;
        }
    }
    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            databaseDAO.update(night)
        }
    }

    // tranform từ LiveData thì nó cũng là LiveData rồi
    // không có tonight thì bật start
    // nếu sử dụng
    /*Sử dụng biến để check trạng thái của button xem là true hay false. Cần nhanh chóng cập nhật nên view
    * ta dùng data binding*/
    val startButtonVisible = Transformations.map(tonight) {
        it == null // nếu đúng sẽ trả về true ngắn ngon k cần if
    }

    // có rồi thì bật stop
    val stopButtonVisible = Transformations.map(tonight) {
        it != null
    }
    // nếu danh sách night không rỗng thì bật
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent:LiveData<Boolean>
        get() = _showSnackBarEvent


    // khi event đó kết thúc ta sẽ reset lại
    fun doneShowSnackBarEvent() {
        _showSnackBarEvent.value = false;
    }



    // khi clear data, reset tonight về ban đầu
    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnackBarEvent.value = true
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            databaseDAO.delete()
        }
    }

    // cancel all coroutines.
    // Khi ViewModel destroyed thì onCleared() đã được gọi
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // điều hướng sang DetailFragment
    private val _navigateSleepDetail = MutableLiveData<Long?>()
    val navigateSleepDetail : LiveData<Long?>
        get() = _navigateSleepDetail

    // handle event click. Khi click vào đó ta sẽ lưu lại key của item đó.
    // Còn key thì sẽ được truyền Fragment
    // khi click vào item ở trên gridlayout
    fun onSleepNightClicked(id:Long){
        _navigateSleepDetail.value = id; // sử dụng navigate lưu id luôn, k cần boolean,
        // để còn truyền id sang cả Fragment Detail
    }

    // khi thực hiện click xong
    fun onSleepDetailNavigated() {
        _navigateSleepDetail.value = null
    }

}

