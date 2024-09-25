package com.example.weatherapp.viewmodel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.viewmodel.settings.SettingsViewModel

class SplashViewModelFactory (private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(repo) as T
        }else
        {throw IllegalArgumentException("Unknown ViewModel class")}
    }
}