package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource(private val weatherDao: WeatherDao) {
    suspend fun insertWeather(weather: Weather) {
        weatherDao.insertWeather(weather)
    }

    suspend fun getWeatherById(weatherId: Long): Weather? {
        return weatherDao.getWeatherById(weatherId)
    }

    fun getAllWeather(): Flow<List<Weather>> {
        return weatherDao.getAllWeather()
    }

    suspend fun insertWeatherForecast(forecast: WeatherForecast) {
        weatherDao.insertWeatherForecast(forecast)
    }

    suspend fun getForecastByDate(forecastDate: Long): WeatherForecast? {
        return weatherDao.getForecastByDate(forecastDate)
    }

    fun getAllWeatherForecast(): Flow<List<WeatherForecast>> {
        return weatherDao.getAllWeatherForecast()
    }

    suspend fun deleteAllWeather() {
        weatherDao.deleteAllWeather()
    }

    suspend fun deleteAllWeatherForecast() {
        weatherDao.deleteAllWeatherForecast()
    }
}