package com.example.weatherapp.data.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IWeatherRepository {
    //Retrofit functions
   // suspend fun getCurrentWeatherBasic(lat: Double, lon: Double): List<Weather>?
    suspend fun fetchCurrentWeather(lat: Double, lon: Double,lang :String): Flow<Response<Weather>>
    suspend fun fetchHourlyForecast(lat: Double, lon: Double,lang :String): Flow<List<WeatherForecast>>
    suspend fun fetchDailyForecast(lat: Double, lon: Double,lang :String): Flow<Response<Weather>>

    // Room functions
    suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather)
    fun getAllFavorites(): Flow<List<FavoriteWeather>>
    suspend fun deleteFavoriteWeather(weather: FavoriteWeather)


    //SharedPref functions

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

    fun getLang():String
    fun setLang(string:String)

    fun getWindSpeedUnit():String
    fun setWindSpeedUnit(string:String)


    fun setNotificationsEnabled(enabled: String)
    fun setLocationEnabled(location: String)
    fun getNotificationsEnabled(): String
    fun getLocationEnabled(): String
}