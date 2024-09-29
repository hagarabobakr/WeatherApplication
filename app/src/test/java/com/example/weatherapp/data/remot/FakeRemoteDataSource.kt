package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FakeRemoteDataSource: IWeatherRemoteDataSourceImp {
    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<List<WeatherForecast>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDailyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        TODO("Not yet implemented")
    }
}