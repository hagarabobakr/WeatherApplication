package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.data.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteWeatherDao {
    @Insert
    suspend fun insert(favoriteWeather: FavoriteWeather)

    @Query("SELECT * FROM favorites")
     fun getAllFavorites(): Flow<List<FavoriteWeather>>

    @Query("DELETE FROM favorites WHERE name = :name")
    suspend fun deleteFavorite(name: String)
}