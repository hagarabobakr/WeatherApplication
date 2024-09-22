package com.example.weatherapp.data.sharedprefrances

interface GlobalSharedPreferenceDataSource {
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