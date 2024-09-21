package com.example.weatherapp.data.model

import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.remot.WeatherRemoteDataSource

class WeatherRepository (private var remoteDataSource: WeatherRemoteDataSource,
                         private var localDataAource: WeatherLocalDataSource
) {

    companion object{
        var instance : WeatherRepository ? = null

        fun getInstance(remote : WeatherRemoteDataSource, local : WeatherLocalDataSource) : WeatherRepository{
            return instance?: synchronized(this){
                val temp = WeatherRepository(remote,local)
                instance=temp
                temp
            }
        }


    }

    suspend fun getCurrentWeather(lat: Double, lon: Double) : List<Weather>?{
        return remoteDataSource.getCurrentWeather(lat,lon)
    }
    suspend fun getMain(lat: Double, lon: Double) : Main?{
        return remoteDataSource.getMain(lat,lon)
    }
}