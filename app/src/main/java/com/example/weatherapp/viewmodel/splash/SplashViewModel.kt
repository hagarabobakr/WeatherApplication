package com.example.weatherapp.viewmodel.splash

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.WeatherRepository

class SplashViewModel(val repo: WeatherRepository) : ViewModel() {
    fun saveLocation(lat: Double, lon: Double) {
        repo.setMapLat(lat.toString())
        repo.setMapLon(lon.toString())
    }
}