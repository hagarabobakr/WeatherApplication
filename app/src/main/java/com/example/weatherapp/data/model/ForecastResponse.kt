package com.example.weatherapp.data.model

data class ForecastResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<WeatherForecast>,
    val city: City
)

data class WeatherForecast(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherElement>,
    val wind: Wind,

)

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