package com.example.weatherapp.data.model

import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp

class WeatherRepository (private var remoteDataSource: WeatherRemoteDataSource,
                         private var localDataSource: WeatherLocalDataSource,
    private var globalSharedPreferenceDataSource: GlobalSharedPreferenceDataSource
) : IWeatherRepository {

    companion object{
        var instance : WeatherRepository ? = null

        fun getInstance(remote : WeatherRemoteDataSource, local : WeatherLocalDataSource, shared: GlobalSharedPreferenceDataSourceImp) : WeatherRepository{
            return instance?: synchronized(this){
                val temp = WeatherRepository(remote,local,shared)
                instance=temp
                temp
            }
        }


    }

    override suspend fun getCurrentWeatherBasic(lat: Double, lon: Double) : List<Weather>?{
        return remoteDataSource.getCurrentWeatherBasic(lat,lon)
    }
    override suspend fun getCurrentWeather(lat: Double, lon: Double, lang : String) : WeatherResponse?{
        return remoteDataSource.getCurrentWeather(lat,lon,lang)
    }
    override suspend fun getMain(lat: Double, lon: Double) : Main?{
        return remoteDataSource.getMain(lat,lon)
    }
    override suspend fun getCityName(lat: Double, lon: Double) : String?{
        return remoteDataSource.getCityName(lat,lon)
    }




//Shared functions
    override fun getTempUnit(): String {
        return globalSharedPreferenceDataSource.getTempUnit()
    }

    override fun setTempUnit(string: String) {
        globalSharedPreferenceDataSource.setTempUnit(string)
    }

    override fun getMapLon(): String {
        return globalSharedPreferenceDataSource.getMapLon()
    }

    override fun setMapLon(string: String) {
        globalSharedPreferenceDataSource.setMapLon(string)
    }

    override fun getMapLat(): String {
        return globalSharedPreferenceDataSource.getMapLat()
    }

    override fun setMapLat(string: String) {
        globalSharedPreferenceDataSource.setMapLat(string)
    }

    override fun getLang(): String {
        return globalSharedPreferenceDataSource.getLang()
    }

    override fun setLang(string: String) {
        globalSharedPreferenceDataSource.setLang(string)
    }

    override fun getWindSpeedUnit(): String {
        return globalSharedPreferenceDataSource.getWindSpeedUnit()
    }

    override fun setWindSpeedUnit(string: String) {
        globalSharedPreferenceDataSource.setWindSpeedUnit(string)
    }
    override fun setNotificationsEnabled(enabled: String) {
        globalSharedPreferenceDataSource.setNotificationsEnabled(enabled)
    }

    override fun setLocationEnabled(location: String) {
        globalSharedPreferenceDataSource.setLocationEnabled(location)
    }



}