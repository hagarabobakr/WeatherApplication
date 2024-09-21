package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel (private val repo : WeatherRepository) : ViewModel() {
    private val _weather = MutableLiveData<List<Weather>?>()
    val weather: LiveData<List<Weather>?> = _weather
    var main : Main? = null
    init {
        getCurrentWeather(10.99,44.34)
    }
    fun getCurrentWeather(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO){
            val w = repo.getCurrentWeather(lat,lon)
            withContext(Dispatchers.Main){
                _weather.postValue(w)
            }
        }

    }
    fun getMain(lat: Double, lon: Double) : Main?{

        viewModelScope.launch {
            main =  repo.getMain(lat,lon)
        }
        return main
    }
}