package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import retrofit2.Response

sealed class ApiState {
    class SuccessCurrent(val data: Response<Weather>) : ApiState()
    class SuccessForecast(val data: List<WeatherForecast>) : ApiState()
    class Failure(val msg: Throwable): ApiState()
    object Loading: ApiState()
}