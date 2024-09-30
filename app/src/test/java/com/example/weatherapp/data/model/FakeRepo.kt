package com.example.weatherapp.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class FakeRepo() :IWeatherRepository {
    val weather1 = FavoriteWeather("Cairo",27.0,"",20,30.0, 31.0, "windy","metric","egypt","en")
    val weather2 = FavoriteWeather("London",28.0,"",5,50.0, 65.0, "rainy","metric","paris","en")
    val localWeatherList = listOf(weather1, weather2).toMutableList()


    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Weather>> {
        val main = Main(
            temp = 25.0,
            tempMin = 20.0,
            grndLevel = 1000,
            humidity = 80,
            pressure = 1012,
            seaLevel = 1015,
            feelsLike = 26.0,
            tempMax = 28.0
        )

        val clouds = Clouds(all = 40)

        val sys = Sys(
            sunrise = 1609459200,
            sunset = 1609502400
        )

        val coord = Coord(lon = lon, lat = lat)

        val weatherList = listOf(
            WeatherElement(
                icon = "01d",
                description = "clear sky",
                main = "Clear",
                id = 1
            )
        )

        val wind = Wind(
            deg = 180,
            speed = 5.0,
            gust = 7.0
        )

        val weatherData = Weather(
            visibility = 10,
            timezone = 3600,
            main = main,
            clouds = clouds,
            sys = sys,
            dt = 1609497600,
            coord = coord,
            weather = weatherList,
            name = "Cairo",
            cod = 200,
            id = 123456,
            base = "stations",
            wind = wind
        )

        val response = Response.success(weatherData)
        return flowOf(response)
    }


    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<List<WeatherForecast>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFavoriteWeather(favoriteWeather: FavoriteWeather) {
        localWeatherList.add(favoriteWeather)
    }

    override fun getAllFavorites(): Flow<List<FavoriteWeather>> {
                return flowOf(localWeatherList)
    }

    override suspend fun deleteFavoriteWeather(weather: FavoriteWeather) {
        localWeatherList.remove(weather)
    }

    override fun getUnit(): String {
        return "metric"
    }

    override fun setUnit(string: String) {
        TODO("Not yet implemented")
    }

    override fun getTempUnit(): String {
        return " temp"
    }

    override fun setTempUnit(string: String) {
        TODO("Not yet implemented")
    }

    override fun getMapLon(): String {
        return " "
    }

    override fun setMapLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getMapLat(): String {
        return " "
    }

    override fun setMapLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getGpsLon(): String {
        return " "
    }

    override fun setGpsLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getGpsLat(): String {
        return " "
    }

    override fun setGpsLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getFavLon(): String {
        return "0.0"
    }

    override fun setFavLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getFavLat(): String {
        return "0.0"
    }

    override fun setFavLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getLang(): String {
        return "en"
    }

    override fun setLang(string: String) {
        TODO("Not yet implemented")
    }

    override fun getWindSpeedUnit(): String {
        return " "
    }

    override fun setWindSpeedUnit(string: String) {
        TODO("Not yet implemented")
    }

    override fun setNotificationsEnabled(enabled: String) {
        TODO("Not yet implemented")
    }

    override fun setLocationEnabled(location: String) {
        TODO("Not yet implemented")
    }

    override fun getNotificationsEnabled(): String {
        return " "
    }

    override fun getLocationEnabled(): String {
        return " "
    }
}