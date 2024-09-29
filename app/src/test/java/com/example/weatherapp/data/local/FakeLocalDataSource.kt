package com.example.weatherapp.data.local

import com.example.weatherapp.data.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource(var weatherData: MutableList<FavoriteWeather>? = mutableListOf()): IWeatherLocalDataSource {
    override suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather) {
        weatherData?.add(favoriteWeather)
    }

    override fun getAllFavorites(): Flow<List<FavoriteWeather>> {
        return flow {
            weatherData?.let { emit(ArrayList(it)) } ?: emit(emptyList())
        }
    }

    override suspend fun deleteFavorite(weather: FavoriteWeather) {
        weatherData?.remove(weather)
    }
}