// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.weatherapp.data.model

import androidx.room.Entity

@Entity(tableName = "weather")
data class Weather (
    val visibility: Long,
    val timezone: Long,
    val main: Main,
    val clouds: Clouds,
    val sys: Sys,
    val dt: Long,
    val coord: Coord,
    val weather: List<WeatherElement>,
    val name: String,
    val cod: Long,
    val id: Long,
    val base: String,
    val wind: Wind
)
@Entity(tableName = "clouds")
data class Clouds (
    val all: Long
)
@Entity(tableName = "coord")
data class Coord (
    val lon: Double,
    val lat: Double
)
@Entity(tableName = "main")
data class Main (
    val temp: Double,
    val tempMin: Double,
    val grndLevel: Long,
    val humidity: Long,
    val pressure: Long,
    val seaLevel: Long,
    val feelsLike: Double,
    val tempMax: Double
)
@Entity(tableName = "sys")
data class Sys (
    val sunrise: Long,
    val sunset: Long
)
@Entity(tableName = "weather_element")
data class WeatherElement (
    val icon: String,
    val description: String,
    val main: String,
    val id: Long
)
@Entity(tableName = "wind")
data class Wind (
    val deg: Long,
    val speed: Double,
    val gust: Double
)
