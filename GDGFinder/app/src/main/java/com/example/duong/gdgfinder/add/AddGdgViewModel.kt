package com.example.duong.gdgfinder.add

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class AddGdgViewModel:ViewModel() {
    private val _showSnackbarEvent = MutableLiveData<Boolean?>()
    val showSnackbarEvent : LiveData<Boolean?>
        get() = _showSnackbarEvent;

    fun doneShowSnackBarEvent() {
        _showSnackbarEvent.value = false;
    }

    fun onSubmitApplication() {
        _showSnackbarEvent.value = true;
    }
}