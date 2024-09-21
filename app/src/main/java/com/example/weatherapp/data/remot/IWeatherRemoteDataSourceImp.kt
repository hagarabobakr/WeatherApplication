package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind

interface IWeatherRemoteDataSourceImp {
    suspend fun getCurrentWeather(lat: Double, lon: Double): List<Weather>?

    suspend fun getMain(lat: Double, lon: Double): Main?
    suspend fun getWind(lat: Double, lon: Double): Wind?
    suspend fun getClouds(lat: Double, lon: Double): Clouds?
    suspend fun getCityName(lat: Double, lon: Double): String?
}