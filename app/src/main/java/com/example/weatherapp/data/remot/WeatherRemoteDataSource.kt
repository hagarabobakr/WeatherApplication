package com.example.weatherapp.data.remot

import android.util.Log
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind

class WeatherRemoteDataSource : IWeatherRemoteDataSourceImp {

    val API_KEY = "7f6dd0097b5662feed4455238a1321a5"
    private val WeatherService: ApiService by lazy {
        RetrofitHelper.getInstance().create(ApiService::class.java)
    }
    companion object {
        @Volatile
        private var instance: WeatherRemoteDataSource? = null

        fun getInstance(): WeatherRemoteDataSource {
            return instance ?: synchronized(this) {
                val tempInstance = instance ?:
                WeatherRemoteDataSource().also { instance = it }
                tempInstance
            }
        }
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): List<Weather>? {
        val response = WeatherService.getCurrentWeather(lat, lon, API_KEY)
        return if (response.isSuccessful) {
            response.body()?.weather
        } else {
            Log.i("TAG", "getCurrentWeather: onFailure")
            null
        }
    }
    override suspend fun getMain(lat: Double, lon: Double) : Main?{
        val response = WeatherService.getMain(lat,lon,API_KEY)
        return if (response.isSuccessful) {
            response.body()?.main
        } else {
            Log.i("TAG", "getMain: onFailure")
            null
        }
    }

    override suspend fun getWind(lat: Double, lon: Double) : Wind?{
        val response = WeatherService.getWind(lat,lon,API_KEY)
        return if (response.isSuccessful) {
            response.body()?.wind
        } else {
            Log.i("TAG", "getMain: onFailure")
            null
        }
    }

    override suspend fun getClouds(lat: Double, lon: Double) : Clouds?{
        val response = WeatherService.getClouds(lat,lon,API_KEY)
        return if (response.isSuccessful) {
            response.body()?.clouds
        } else {
            Log.i("TAG", "getMain: onFailure")
            null
        }
    }

    override suspend fun getCityName(lat: Double, lon: Double) : String?{
        val response = WeatherService.getCityName(lat,lon,API_KEY)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.i("TAG", "getMain: onFailure")
            null
        }
    }


}