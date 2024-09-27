package com.example.weatherapp.data.local

import androidx.room.TypeConverter
import com.example.weatherapp.data.model.WeatherElement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromWeatherElementList(value: List<WeatherElement>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWeatherElementList(value: String?): List<WeatherElement>? {
        val listType = object : TypeToken<List<WeatherElement>>() {}.type
        return gson.fromJson(value, listType)
    }
}