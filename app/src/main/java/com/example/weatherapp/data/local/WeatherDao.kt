package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM weather WHERE id = :weatherId")
    suspend fun getWeatherById(weatherId: Long): Weather?

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM weather")
    fun getAllWeather(): Flow<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecast(forecast: WeatherForecast)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM weather_forecast WHERE dt = :forecastDate")
    suspend fun getForecastByDate(forecastDate: Long): WeatherForecast?

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM weather_forecast")
    fun getAllWeatherForecast(): Flow<List<WeatherForecast>>

    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()

    @Query("DELETE FROM weather_forecast")
    suspend fun deleteAllWeatherForecast()
}