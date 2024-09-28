package com.example.weatherapp.data.sharedprefrances

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
}