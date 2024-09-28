package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorites")
data class FavoriteWeather(
    @PrimaryKey
    @NotNull
    val name: String,
    val temp: Double,
    val icon: String,
    val dt: Long,
    val lon: Double?,
    val lat: Double?,
    val description: String,
    var unite : String,
    val country: String,
    val lang: String
)