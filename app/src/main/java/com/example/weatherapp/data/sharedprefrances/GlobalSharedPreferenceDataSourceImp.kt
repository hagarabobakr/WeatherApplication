package com.example.weatherapp.data.sharedprefrances

import android.content.SharedPreferences

class GlobalSharedPreferenceDataSourceImp(private val sharedPreferences: SharedPreferences):GlobalSharedPreferenceDataSource {
    private val tempUnitKey = "TEMP_UNIT"
    private val mapLonKey = "MAP_LON"
    private val mapLatKey = "MAP_LAT"
    private val favLonKey = "FAV_LON"
    private val favLatKey = "FAV_LAT"
    private val gpsLonKey = "GPS_LON"
    private val gpsLatKey = "GPS_LAT"
    private val langKey = "LANG"
    private val windSpeedUnitKey = "WIND_SPEED_UNIT"
    private val unitKey = "UNIT"
    private val notificationsEnabledKey = "NOTIFICATIONS_ENABLED"
    private val locationEnabledKey = "LOCATION_ENABLED"
    override fun getUnit(): String {
        return sharedPreferences.getString(unitKey, "metric") ?: "metric"
    }

    override fun setUnit(string: String) {
        sharedPreferences.edit().putString(unitKey, string).apply()
    }

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

    override fun getGpsLon(): String {
        return sharedPreferences.getString(gpsLonKey, "0.0") ?: "0.0"
    }

    override fun setGpsLon(string: String) {
        sharedPreferences.edit().putString(gpsLonKey, string).apply()
    }

    override fun getGpsLat(): String {
        return sharedPreferences.getString(gpsLatKey, "0.0") ?: "0.0"
    }

    override fun setGpsLat(string: String) {
        sharedPreferences.edit().putString(gpsLatKey, string).apply()
    }

    override fun getFavLon(): String {
        return sharedPreferences.getString(favLonKey, "0.0") ?: "0.0"
    }

    override fun setFavLon(string: String) {
        sharedPreferences.edit().putString(favLonKey, string).apply()
    }

    override fun getFavLat(): String {
        return sharedPreferences.getString(favLatKey, "0.0") ?: "0.0"
    }

    override fun setFavLat(string: String) {
        sharedPreferences.edit().putString(favLatKey, string).apply()
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
    override fun setNotificationsEnabled(string: String) {
        sharedPreferences.edit().putString(notificationsEnabledKey, string).apply()
    }

    override fun getNotificationsEnabled(): String {
        return sharedPreferences.getString(notificationsEnabledKey, "Disable") ?: "Disable"
    }

    override fun setLocationEnabled(string: String) {
        sharedPreferences.edit().putString(locationEnabledKey, string).apply()
    }

    override fun getLocationEnabled(): String {
        return sharedPreferences.getString(locationEnabledKey, "Map") ?: "Map"
    }
}