package com.example.weatherapp.data.model

import androidx.room.Entity

@Entity(tableName = "forecast_response")
data class ForecastResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<WeatherForecast>,
    val city: City
)
@Entity(tableName = "weather_forecast")
data class WeatherForecast(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherElement>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility : Int,
    val pop : Double,
    val sys: Sys,
    val dt_txt : String

)
@Entity(tableName = "city")
data class City(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)