package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.model.FavoriteWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteWeather: FavoriteWeather)

    @Query("SELECT * FROM favorites")
     fun getAllFavorites(): Flow<List<FavoriteWeather>>

    @Delete
    suspend fun deleteFavorite(weather: FavoriteWeather): Int

}