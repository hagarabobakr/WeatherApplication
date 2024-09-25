package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.CloudsResponse
import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.data.model.ForecastResponse
import com.example.weatherapp.data.model.MainResponse
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.model.WindResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<Weather>

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<ForecastResponse>


    @GET("forecast")
    suspend fun getDailyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") lang: String,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<Weather>



    /*@GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String? = null,
        @Query("units") units: String? = null,
        @Query("lang") lang: String? = null,
        @Query("appid") apiKey: String): Response<WeatherResponse>*/
}



