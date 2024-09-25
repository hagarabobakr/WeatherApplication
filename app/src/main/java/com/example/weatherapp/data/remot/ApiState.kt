package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.ForecastResponse
import com.example.weatherapp.data.model.Weather
import retrofit2.Response

sealed class ApiState {
    class SuccessCurrent(val data: Response<Weather>) : ApiState()
    class SuccessForecast(val data: Response<ForecastResponse>) : ApiState()
    class Failure(val msg: Throwable): ApiState()
    object Loading: ApiState()
}