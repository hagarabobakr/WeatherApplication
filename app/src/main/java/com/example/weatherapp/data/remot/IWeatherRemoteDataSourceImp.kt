package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.ForecastResponse
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.model.Wind
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IWeatherRemoteDataSourceImp {
    //suspend fun getCurrentWeatherBasic(lat: Double, lon: Double): List<Weather>?
    suspend fun fetchCurrentWeather(lat: Double, lon: Double,lang :String): Flow<Response<Weather>>
    suspend fun fetchHourlyForecast(lat: Double, lon: Double,lang :String): Flow<Response<ForecastResponse>>
    suspend fun fetchDailyForecast(lat: Double, lon: Double,lang :String): Flow<Response<Weather>>
    /* suspend fun getMain(lat: Double, lon: Double): Main?
     suspend fun getWind(lat: Double, lon: Double): Wind?
     suspend fun getClouds(lat: Double, lon: Double): Clouds?
     suspend fun getCityName(lat: Double, lon: Double): String?*/
}