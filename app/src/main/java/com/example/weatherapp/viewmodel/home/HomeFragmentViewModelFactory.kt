package com.example.weatherapp.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.model.WeatherRepository

class HomeFragmentViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFragmentViewModel(repo) as T
        }else
        {throw IllegalArgumentException("Unknown ViewModel class")}
    }
}