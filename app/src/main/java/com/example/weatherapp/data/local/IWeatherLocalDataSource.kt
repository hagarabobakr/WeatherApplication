package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface IWeatherLocalDataSource {
    suspend fun insertWeather(weather: Weather)

    suspend fun getWeatherById(weatherId: Long): Weather?
    fun getAllWeather(): Flow<List<Weather>>

    suspend fun insertWeatherForecast(forecast: WeatherForecast)

    suspend fun getForecastByDate(forecastDate: Long): WeatherForecast?
    fun getAllWeatherForecast(): Flow<List<WeatherForecast>>

    suspend fun deleteAllWeather()

    suspend fun deleteAllWeatherForecast()
}