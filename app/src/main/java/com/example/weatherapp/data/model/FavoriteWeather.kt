package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteWeather(
    @PrimaryKey
    val name: String,
    val lon: Double,
    val lat: Double,
    val description: String,
    var unite : String,
    val country: String,
    val lang: String
)