package com.example.duong.guesstheword.screens.score

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

// class có trách nhiệm khởi tạo object ScoreViewModel
// class này sẽ implements từ ViewModelProvider.Factory --> Interface
class ScoreViewModelFactory(private val finalScore:Int) : ViewModelProvider.Factory {
    // create để return ra object ViewModel
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // sẽ trả về object ScoreViewModel
        // check xem modelClass có được gán (assigable) bởi ScoreViewModel class hay không.
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        // do ViewModel class có tham số nên ViewModelFactory cũng cần có tham số
        throw IllegalArgumentException("Unknown ViewModel class")
        // else thì throw ra exception Unknown ViewModel
    }
}