package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource(private val favoriteWeatherDao: FavoriteWeatherDao) :
    IWeatherLocalDataSource {

    override suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather) {
        favoriteWeatherDao.insert(favoriteWeather)
    }

    override fun getAllFavorites(): Flow<List<FavoriteWeather>> {
        return favoriteWeatherDao.getAllFavorites()
    }

    override suspend fun deleteFavorite(name: String) {
        favoriteWeatherDao.deleteFavorite(name)
    }
}