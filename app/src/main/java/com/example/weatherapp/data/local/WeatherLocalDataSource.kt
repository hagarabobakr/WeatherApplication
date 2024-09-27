package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource(private val weatherDao: WeatherDao) : IWeatherLocalDataSource {
    override suspend fun insertWeather(weather: Weather) {
        weatherDao.insertWeather(weather)
    }

    override suspend fun getWeatherById(weatherId: Long): Weather? {
        return weatherDao.getWeatherById(weatherId)
    }

    override fun getAllWeather(): Flow<List<Weather>> {
        return weatherDao.getAllWeather()
    }

    override suspend fun insertWeatherForecast(forecast: WeatherForecast) {
        weatherDao.insertWeatherForecast(forecast)
    }

    override suspend fun getForecastByDate(forecastDate: Long): WeatherForecast? {
        return weatherDao.getForecastByDate(forecastDate)
    }

    override fun getAllWeatherForecast(): Flow<List<WeatherForecast>> {
        return weatherDao.getAllWeatherForecast()
    }

    override suspend fun deleteAllWeather() {
        weatherDao.deleteAllWeather()
    }

    override suspend fun deleteAllWeatherForecast() {
        weatherDao.deleteAllWeatherForecast()
    }
}