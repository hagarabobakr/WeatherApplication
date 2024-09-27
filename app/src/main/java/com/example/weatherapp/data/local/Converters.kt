package com.example.weatherapp.data.local

import androidx.room.TypeConverter
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.WeatherElement
import com.example.weatherapp.data.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()


    @TypeConverter
    fun fromWeatherElementList(value: List<WeatherElement>?): String? {
        return gson.toJson(value) // Convert list to JSON
    }

    @TypeConverter
    fun toWeatherElementList(value: String?): List<WeatherElement>? {
        val listType = object : TypeToken<List<WeatherElement>>() {}.type
        return gson.fromJson(value, listType) // Convert JSON to list
    }

    @TypeConverter
    fun fromMain(main: Main?): String? {
        return gson.toJson(main)
    }

    @TypeConverter
    fun toMain(mainString: String?): Main? {
        val type = object : TypeToken<Main>() {}.type
        return gson.fromJson(mainString, type)
    }

    @TypeConverter
    fun fromClouds(clouds: Clouds?): String? {
        return gson.toJson(clouds)
    }

    @TypeConverter
    fun toClouds(cloudsString: String?): Clouds? {
        val type = object : TypeToken<Clouds>() {}.type
        return gson.fromJson(cloudsString, type)
    }

    @TypeConverter
    fun fromSys(sys: Sys?): String? {
        return gson.toJson(sys)
    }

    @TypeConverter
    fun toSys(sysString: String?): Sys? {
        val type = object : TypeToken<Sys>() {}.type
        return gson.fromJson(sysString, type)
    }

    @TypeConverter
    fun fromCoord(coord: Coord?): String? {
        return gson.toJson(coord)
    }

    @TypeConverter
    fun toCoord(coordString: String?): Coord? {
        val type = object : TypeToken<Coord>() {}.type
        return gson.fromJson(coordString, type)
    }

    @TypeConverter
    fun fromWind(wind: Wind?): String? {
        return gson.toJson(wind)
    }

    @TypeConverter
    fun toWind(windString: String?): Wind? {
        val type = object : TypeToken<Wind>() {}.type
        return gson.fromJson(windString, type)
    }

    @TypeConverter
    fun fromCity(city: City?): String? {
        return gson.toJson(city)
    }

    @TypeConverter
    fun toCity(cityString: String?): City? {
        val type = object : TypeToken<City>() {}.type
        return gson.fromJson(cityString, type)
    }
}