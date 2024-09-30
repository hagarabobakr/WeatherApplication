package com.example.weatherapp.data.sharedprefrances

import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast

interface GlobalSharedPreferenceDataSource {
//unit
    fun getUnit():String
    fun setUnit(string:String)
//temp
    fun getTempUnit():String
    fun setTempUnit(string:String)
//map
    fun getMapLon():String
    fun setMapLon(string:String)

    fun getMapLat():String
    fun setMapLat(string:String)
//Gps

    fun getGpsLon():String
    fun setGpsLon(string:String)

    fun getGpsLat():String
    fun setGpsLat(string:String)
//fav
    fun getFavLon():String
    fun setFavLon(string:String)

    fun getFavLat():String
    fun setFavLat(string:String)

//lang
    fun getLang():String
    fun setLang(string:String)

    fun getWindSpeedUnit():String
    fun setWindSpeedUnit(string:String)
    fun setNotificationsEnabled(string: String)
    fun getNotificationsEnabled(): String
    fun setLocationEnabled(string: String)
    fun getLocationEnabled(): String


    //home weather
    fun saveFavoriteWeather(favoriteWeather: FavoriteWeather)
    fun getFavoriteWeather(): FavoriteWeather?


    fun saveWeather(weather: Weather)
    fun getWeather(): Weather?


    fun saveHourlyWeather(hourlyWeather: List<WeatherForecast>)
    fun getHourlyWeather(): List<WeatherForecast>?

    fun saveDailyWeather(dailyWeather: List<WeatherForecast>)
    fun getDailyWeather(): List<WeatherForecast>?

}