package com.example.weatherapp.viewmodel.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModel

class MapViewModelFactory (private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(repo) as T
        }else
        {throw IllegalArgumentException("Unknown ViewModel class")}
    }
}