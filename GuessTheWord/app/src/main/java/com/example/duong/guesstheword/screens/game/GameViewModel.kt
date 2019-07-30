package com.example.duong.guesstheword.screens.game

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log

class GameViewModel: ViewModel() {
    // biến timer, khowir taoj trong init
    private val timer:CountDownTimer
    // dùng để định nghĩa các biến static , method static như java
    // bên trong class đó có thể gọi trực tiếp, bên ngoài class thì gọi như static, nó thuộc về lớp
    // const
    companion object {
        // thời gian khi game kết thúc
        private const val DONE = 0L;
        // khoảng thời gian đếm ngược (ms)
        private const val ONE_SECOND = 1000L;
        // tổng thời gian của game
        private const val COUNTDOWN_TIME = 60000L;
    }


    //coundown time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime :LiveData<Long>
        get(){
            return _currentTime;
        }


    // word current
    // cho biến word thành MutableLiveData. Giá trị bây giờ sẽ có kiểu LiveData
    private val _word = MutableLiveData<String>();
    val word: LiveData<String>  // và giờ truy xuất biến thì sử dụng .value,
        // type là LiveData nên sử dụng .value để truy xuất
        get(){
            return _word;
        }
        // cast từ LiveData về MutableLiveData

    // score current
    private var _score = MutableLiveData<Int>(); // lưu dữ liệu
    val score: LiveData<Int>  // để truy nhập đến _score
        get(){
            return _score;
        }

    // The String version of the current time
    // ánh xạ time sang timeString để hiển thị
    // map đã convert LiveData object là currentTime sang currentTimeString
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time) // hàm ánh xạ
        // format sang time hh:mm:ss để hiển thị
    }

    val wordHint = Transformations.map(word) {word ->
        "Current word has " + word.length + " letters!"
    }

    // create variable check hết list chưa
    private val _endGameFinish = MutableLiveData<Boolean>();
    val endGameFinish: LiveData<Boolean>
        get(){
            return _endGameFinish;
        }

    // khởi tạo list cho phép null trước xong gán sau
    lateinit var listWord : MutableList<String> ;
    // init trong viewmodel như là contructor
    init {
        _word.value = "";
        _score.value = 0;

       // Log.i("GameViewModel","GameViewModel created!");
        // init listword (khởi tạo danh sách )
        initListWord();
        // chuyển sang từ kế tiếp, và get word để display
        nextWord();

        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long)
            {
                // millisUntil là thời gian cho đến khi kết thúc và đổi ra (s)
                _currentTime.value = millisUntilFinished/ONE_SECOND
            }

            override fun onFinish() {
                // thời gian sẽ kết thúc
                _currentTime.value = DONE
                onGameFinish()
            }
        }

        timer.start()
    }

    // init danh sach tu
    private fun initListWord() {
        listWord = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        // xáo trộn danh sách
        listWord.shuffle();

    }

    //chuyển sang từ kế tiếp
    private fun nextWord() {
        if(listWord.isEmpty()) {
            initListWord(); // khoiwr tao lai bao gio het gio thi het
        }
        else {
            // select word and remove word khỏi list
            _word.value = listWord.removeAt(0);
        }

    }
    // khi thanh cong thi chuyen từ va tang diem
    // code processing data
    fun onCorrect () {
        if(!listWord.isEmpty()) {
            // cộng đi 1, score biến chỉ đọc k thay đổi được giá trị nên gán vào biến sao lưu
            _score.value = (score.value)?.plus(1);
        }
        nextWord();
    }
    // skip thì giảm điểm và chuyển từ
    fun onSkip(){
        if(!listWord.isEmpty()) {
            // khi mà thêm check null cho biến thì thêm vào lúc thay đổi
            // không phải phía gán.
            _score.value = (score.value)?.minus(1);
        }
        nextWord();
    }
    // check end game change state
    fun onGameFinish() {
        _endGameFinish.value = true;
    }

    // gọi trước khi ViewModel destroyed
    override fun onCleared() {
        super.onCleared()
        // hủy timer để tránh rò rỉ bộ nhớ
        timer.cancel()
//        Log.i("GameViewModel","GameViewModel Destroyed!");
    }
}