package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

interface IWeatherLocalDataSource {
    suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather)
    fun getAllFavorites(): Flow<List<FavoriteWeather>>

    suspend fun deleteFavorite(weather: FavoriteWeather)
}