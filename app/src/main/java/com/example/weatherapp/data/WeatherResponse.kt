package com.example.weatherapp.data

import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int

)
data class CurrentWeatherResponse(val weather: List<Weather>)
data class WindResponse(val wind : Wind)
data class MainResponse(val main : Main)
data class CloudsResponse(val clouds : Clouds)