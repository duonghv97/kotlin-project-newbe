package com.example.duong.guesstheword.screens.score

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

// extends từ ViewModel, class viewmodel sẽ chứa các xử lý liên quan đến data
class ScoreViewModel(finalScore:Int) : ViewModel() {
    private var _score = MutableLiveData<Int>() // khai báo kiểu và khởi tạo luôn rồi
    val score : LiveData<Int> // kiểu LiveData nhưng chưa tạo đối tượng
        get(){
            return _score;
        }
    private var _playAgain = MutableLiveData<Boolean>()
    val playAgain : LiveData<Boolean>
        get(){
            return _playAgain;
        }
    // do là dữ liệu nó thực hiện ở get/set value rồi.
    // Còn nếu gán trực tiếp mà dùng val thì sai
    init {
        _score.value = finalScore;
    }

    fun onPlayAgain() {
        _playAgain.value = true;
    }
    fun onPlayAgainResetStatus() {
        _playAgain.value = false;
    }
}