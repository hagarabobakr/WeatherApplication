package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.data.model.FavoriteWeather

@Dao
interface FavoriteWeatherDao {
    @Insert
    suspend fun insert(favoriteWeather: FavoriteWeather)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteWeather>

    @Query("DELETE FROM favorites WHERE name = :name")
    suspend fun deleteFavorite(name: String)
}