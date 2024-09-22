package com.example.weatherapp.data.sharedprefrances

import android.content.SharedPreferences

class GlobalSharedPreferenceDataSourceImp(private val sharedPreferences: SharedPreferences):GlobalSharedPreferenceDataSource {
    private val tempUnitKey = "TEMP_UNIT"
    private val mapLonKey = "MAP_LON"
    private val mapLatKey = "MAP_LAT"
    private val langKey = "LANG"
    private val windSpeedUnitKey = "WIND_SPEED_UNIT"

    override fun getTempUnit(): String {
        return sharedPreferences.getString(tempUnitKey, "Celsius") ?: "Celsius"
    }

    override fun setTempUnit(string: String) {
        sharedPreferences.edit().putString(tempUnitKey, string).apply()
    }

    override fun getMapLon(): String {
        return sharedPreferences.getString(mapLonKey, "0.0") ?: "0.0"
    }

    override fun setMapLon(string: String) {
        sharedPreferences.edit().putString(mapLonKey, string).apply()
    }

    override fun getMapLat(): String {
        return sharedPreferences.getString(mapLatKey, "0.0") ?: "0.0"
    }

    override fun setMapLat(string: String) {
        sharedPreferences.edit().putString(mapLatKey, string).apply()
    }

    override fun getLang(): String {
        return sharedPreferences.getString(langKey, "en") ?: "en"
    }

    override fun setLang(string: String) {
        sharedPreferences.edit().putString(langKey, string).apply()
    }

    override fun getWindSpeedUnit(): String {
        return sharedPreferences.getString(windSpeedUnitKey, "m/s") ?: "m/s"
    }

    override fun setWindSpeedUnit(string: String) {
        sharedPreferences.edit().putString(windSpeedUnitKey, string).apply()
    }
}