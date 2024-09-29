package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeRemoteDataSource(private var weatherResponse: Response<Weather>? = null,
                           private var forecastResponse: List<WeatherForecast>? = null):
    IWeatherRemoteDataSourceImp {
    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        return flow {
            weatherResponse?.let {
                emit(it)
            } ?: emit(Response.error(404, null))
        }.catch { e ->
            emit(Response.error(500, null))
        }
    }

    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<List<WeatherForecast>> {
        return flow {
            forecastResponse?.let {
                emit(it)
            } ?: throw Exception("No forecast data available")
        }.catch { e ->
            throw e
        }


}
    }