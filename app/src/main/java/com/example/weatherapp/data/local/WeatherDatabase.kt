package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast

@TypeConverters(Converters::class)
@Database(entities = [Weather::class, WeatherForecast::class, City::class], version = 1)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}