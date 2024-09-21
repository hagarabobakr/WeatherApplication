package com.example.weatherapp.data.remot

import com.example.weatherapp.data.model.CloudsResponse
import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.data.model.MainResponse
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
        @Query("appid") apiKey: String): Response<CurrentWeatherResponse>

    @GET("main")
    suspend fun getMain(@Query("lat") latitude: Double,
                        @Query("lon") longitude: Double,
                        @Query("appid") apiKey: String): Response<MainResponse>

    @GET("wind")
    suspend fun getWind(@Query("lat") latitude: Double,
                        @Query("lon") longitude: Double,
                        @Query("appid") apiKey: String): Response<WindResponse>

    @GET("clouds")
    suspend fun getClouds(@Query("lat") latitude: Double,
                          @Query("lon") longitude: Double,
                          @Query("appid") apiKey: String): Response<CloudsResponse>

    @GET("name")
    suspend fun getCityName(@Query("lat") latitude: Double,
                            @Query("lon") longitude: Double,
                            @Query("appid") apiKey: String): Response<String>
}
object RetrofitHelper {

    val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"

    // Create OkHttpClient to add logging or other interceptors
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)  // Add the OkHttp client here
            .baseUrl(BASE_URL)
            .build()
    }

    //val service = retrofitInstance.create(ApiService::class.java)
}



