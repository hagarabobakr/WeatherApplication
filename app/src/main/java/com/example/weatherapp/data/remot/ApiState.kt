package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.Weather

sealed class ApiState {
    class Success(val data: List<Weather>): ApiState()
    class Failure(val msg: Throwable): ApiState()
    object Loading: ApiState()
}