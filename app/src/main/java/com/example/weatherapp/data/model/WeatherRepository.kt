package com.example.weatherapp.data.model

import com.example.weatherapp.API_KEY
import com.example.weatherapp.data.local.IWeatherLocalDataSource
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.remot.IWeatherRemoteDataSourceImp
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class WeatherRepository (private var remoteDataSource: IWeatherRemoteDataSourceImp,
                         private var localDataSource: IWeatherLocalDataSource,
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



    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        return remoteDataSource.fetchCurrentWeather(lat,lon,lang,unit)
    }

    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<List<WeatherForecast>> {
        return remoteDataSource.fetchHourlyForecast(lat,lon,lang,unit)
    }

    /*override suspend fun fetchDailyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        return remoteDataSource.fetchDailyForecast(lat,lon,lang,unit)
    }*/


    // Room functions
    override suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather) {
        localDataSource.addFavoriteWeather(favoriteWeather)
    }

    override fun getAllFavorites(): Flow<List<FavoriteWeather>> {
        return localDataSource.getAllFavorites()
    }

    override suspend fun deleteFavoriteWeather(weather: FavoriteWeather) {
        localDataSource.deleteFavorite(weather)
    }

    override fun getUnit(): String {
        return globalSharedPreferenceDataSource.getUnit()
    }

    override fun setUnit(string: String) {
        globalSharedPreferenceDataSource.setUnit(string)
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

    override fun getGpsLon(): String {
        return globalSharedPreferenceDataSource.getGpsLon()
    }

    override fun setGpsLon(string: String) {
        globalSharedPreferenceDataSource.setGpsLon(string)
    }

    override fun getGpsLat(): String {
        return globalSharedPreferenceDataSource.getGpsLat()
    }

    override fun setGpsLat(string: String) {
        globalSharedPreferenceDataSource.setGpsLat(string)
    }

    override fun getFavLon(): String {
      return  globalSharedPreferenceDataSource.getFavLon()
    }

    override fun setFavLon(string: String) {
        globalSharedPreferenceDataSource.setFavLon(string)
    }

    override fun getFavLat(): String {
        return globalSharedPreferenceDataSource.getFavLat()
    }

    override fun setFavLat(string: String) {
        globalSharedPreferenceDataSource.setFavLat(string)
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

    override fun getNotificationsEnabled(): String {
        return globalSharedPreferenceDataSource.getNotificationsEnabled()
    }
    override fun setLocationEnabled(location: String) {
        globalSharedPreferenceDataSource.setLocationEnabled(location)
    }
    override fun getLocationEnabled(): String {
        return globalSharedPreferenceDataSource.getLocationEnabled()
    }


}