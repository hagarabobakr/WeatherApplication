package com.example.weatherapp.data.model

interface IWeatherRepository {
    //Retrofit functions
    suspend fun getCurrentWeatherBasic(lat: Double, lon: Double): List<Weather>?
    suspend fun getCurrentWeather(lat: Double, lon: Double, lang: String): WeatherResponse?
    suspend fun getMain(lat: Double, lon: Double): Main?
    suspend fun getCityName(lat: Double, lon: Double): String?

    //SharedPref functions

    fun getTempUnit():String
    fun setTempUnit(string:String)

    fun getMapLon():String
    fun setMapLon(string:String)

    fun getMapLat():String
    fun setMapLat(string:String)

    fun getLang():String
    fun setLang(string:String)

    fun getWindSpeedUnit():String
    fun setWindSpeedUnit(string:String)


}