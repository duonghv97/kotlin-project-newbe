package com.example.duong.getdatainternet.detail

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.duong.getdatainternet.network.MarsProperty

class DetailViewModelFactory(
    private val marsProperty : MarsProperty,
    private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(marsProperty, application) as T
        }
        throw IllegalArgumentException("Anknown ViewModel class");
    }
}