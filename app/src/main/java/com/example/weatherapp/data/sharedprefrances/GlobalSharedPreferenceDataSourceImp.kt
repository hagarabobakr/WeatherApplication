package com.example.weatherapp.data.sharedprefrances

import android.content.SharedPreferences
import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import com.google.gson.Gson

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
    private val favoriteWeatherKey = "FAVORITE_WEATHER"
    private val weatherKey = "WEATHER"
    private val hourlyWeatherKey = "HOURLY_WEATHER"
    private val dailyWeatherKey = "DAILY_WEATHER"

    override fun saveHourlyWeather(hourlyWeather: List<WeatherForecast>) {
        val json = Gson().toJson(hourlyWeather)
        sharedPreferences.edit().putString(hourlyWeatherKey, json).apply()
    }

    override fun getHourlyWeather(): List<WeatherForecast>? {
        val json = sharedPreferences.getString(hourlyWeatherKey, null) ?: return null
        return Gson().fromJson(json, Array<WeatherForecast>::class.java).toList()
    }

    override fun saveDailyWeather(dailyWeather: List<WeatherForecast>) {
        val json = Gson().toJson(dailyWeather)
        sharedPreferences.edit().putString(dailyWeatherKey, json).apply()
    }

    override fun getDailyWeather(): List<WeatherForecast>? {
        val json = sharedPreferences.getString(dailyWeatherKey, null) ?: return null
        return Gson().fromJson(json, Array<WeatherForecast>::class.java).toList()
    }


    override fun saveWeather(weather: Weather) {
        val json = Gson().toJson(weather)
        sharedPreferences.edit().putString(weatherKey, json).apply()
    }

    override fun getWeather(): Weather? {
        val json = sharedPreferences.getString(weatherKey, null) ?: return null
        return Gson().fromJson(json, Weather::class.java)
    }
    override fun saveFavoriteWeather(favoriteWeather: FavoriteWeather) {
        val json = Gson().toJson(favoriteWeather)
        sharedPreferences.edit().putString(favoriteWeatherKey, json).apply()
    }

    override fun getFavoriteWeather(): FavoriteWeather? {
        val json = sharedPreferences.getString(favoriteWeatherKey, null) ?: return null
        return Gson().fromJson(json, FavoriteWeather::class.java)
    }

    override fun getUnit(): String {
        return sharedPreferences.getString(unitKey, "metric") ?: "standard"
    }

    override fun setUnit(string: String) {
        sharedPreferences.edit().putString(unitKey, string).apply()
    }

    override fun getTempUnit(): String {
        return sharedPreferences.getString(tempUnitKey, "C") ?: "C"
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
        return sharedPreferences.getString(windSpeedUnitKey, "M/S") ?: "M/S"
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