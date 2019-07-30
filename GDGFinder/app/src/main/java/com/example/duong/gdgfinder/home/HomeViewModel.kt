package com.example.duong.gdgfinder.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch : MutableLiveData<Boolean>
        get() = _navigateToSearch

    fun onNavigateToSearchDone() {
        _navigateToSearch.value = false;
    }

    fun onFabClicked() {
        _navigateToSearch.value = true;
    }

}