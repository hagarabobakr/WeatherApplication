package com.example.weatherapp.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeFragmentViewModel (private val repo : WeatherRepository) : ViewModel() {
    private val TAG = "HomeFragmentViewModel"

    //get current weather
    private val _currentWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val currentWeatherStateFlow: StateFlow<ApiState> = _currentWeatherStateFlow

    //get hourly weather
    private val _hourlyWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val hourlyWeatherStateFlow: StateFlow<ApiState> = _hourlyWeatherStateFlow

    //get daily weather
    private val _dailyWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val dailyWeatherStateFlow: StateFlow<ApiState> = _dailyWeatherStateFlow


    init {
        getCurrentWeather(2.5,5.5,"en")
        //getCurrentWeather(2.5,5.5,"ar")
        getHourlyWeather(2.5,5.5,"en")
        //getHourlyWeather(2.5,5.5,"ar")
        getDailyWeather(25.5,55.5,"en")
        //getDailyWeather(2.5,5.5,"ar")
    }
    fun getCurrentWeather(lat: Double, lon: Double,lang : String) {
       Log.i(TAG, "getCurrentWeather: ")
        viewModelScope.launch{
             repo.fetchCurrentWeather(lat,lon,lang)
          .onStart {
                    _currentWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _currentWeatherStateFlow.value = ApiState.Failure(e)
                }.collect{weather->
                     Log.i(TAG, "getCurrentWeather: ${weather.body()}")
                     _currentWeatherStateFlow.value = ApiState.SuccessCurrent(weather)
                    
                 }
           

    }
    }

    fun getHourlyWeather(lat: Double, lon: Double,lang : String) {
        Log.i(TAG, "getCurrentWeather: ")
        viewModelScope.launch{
            repo.fetchHourlyForecast(lat,lon,lang)
                .onStart {
                    _hourlyWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _hourlyWeatherStateFlow.value = ApiState.Failure(e)
                }.collect{weather->
                    Log.i(TAG, "getHourlyWeather: ${weather.body()}")
                    _hourlyWeatherStateFlow.value = ApiState.SuccessForecast(weather)
                }


        }
    }

    fun getDailyWeather(lat: Double, lon: Double,lang : String) {
        Log.i(TAG, "get Daily Weather: ")
        viewModelScope.launch{
            repo.fetchDailyForecast(lat,lon,lang)
                .onStart {
                    _dailyWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _dailyWeatherStateFlow.value = ApiState.Failure(e)
                }.collect{weather->
                    Log.i(TAG, "get Daily Weather in view model: ${weather.body()}")
                    _dailyWeatherStateFlow.value = ApiState.SuccessCurrent(weather)
                }


        }
    }
}