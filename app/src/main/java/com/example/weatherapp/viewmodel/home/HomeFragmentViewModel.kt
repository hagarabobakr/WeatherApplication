package com.example.weatherapp.viewmodel.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.model.Wind
import com.example.weatherapp.data.remot.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragmentViewModel (val repo : WeatherRepository) : ViewModel() {
    private val TAG = "HomeFragmentViewModel"

    //get current weather
    private val _currentWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val currentWeatherStateFlow: StateFlow<ApiState> = _currentWeatherStateFlow

    //get hourly weather
    private val _hourlyWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val hourlyWeatherStateFlow: StateFlow<ApiState> = _hourlyWeatherStateFlow

    //get daily weather
    private val _dailyWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val dailyWeatherStateFlow: StateFlow<ApiState> = _dailyWeatherStateFlow


    init {
        fetchWeatherData()
        //getFavorites()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchWeatherData() {
        val locationType = repo.getLocationEnabled()
        val lat: Double
        val lon: Double

        if (locationType == "GPS") {
            lat = repo.getGpsLat().toDouble()
            lon = repo.getGpsLon().toDouble()
        } else {
            lat = repo.getMapLat().toDouble()
            lon = repo.getMapLon().toDouble()
        }

        getCurrentWeather(lat, lon, repo.getLang(),repo.getUnit())
        getHourlyWeather(lat, lon, repo.getLang(),repo.getUnit())

        Log.i(TAG, "fetchWeatherData: "+ repo.getUnit())
        Log.i(TAG, "fetchWeatherData: "+ repo.getWindSpeedUnit())
       // getDailyWeather(lat, lon, repo.getLang())
    }
    fun getCurrentWeather(lat: Double, lon: Double, lang: String, unit: String) {
        Log.i(TAG, "getCurrentWeather: ")
        viewModelScope.launch {
            // Fetch current weather data from the API
            repo.fetchCurrentWeather(lat, lon, lang, unit)
                .onStart {
                    // Set loading state before data fetching
                    _currentWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    // Handle any errors that occur during data fetching
                    _currentWeatherStateFlow.value = ApiState.Failure(e)
                }.collect { weather ->
                    Log.i(TAG, "getCurrentWeather: ${weather.body()}")

                    // Construct the icon URL for the weather
                    val iconUrl = "https://openweathermap.org/img/wn/${weather.body()?.weather?.get(0)?.icon}.png"

                    // Create a FavoriteWeather object with the fetched data
                    val favoriteWeather = FavoriteWeather(
                        name = weather.body()?.name ?: " ",
                        temp = weather.body()?.main?.temp ?: 23.0,
                        dt = weather.body()?.dt ?: 0,
                        icon = iconUrl,
                        lon = weather.body()?.coord?.lon,
                        lat = weather.body()?.coord?.lat,
                        description = weather.body()?.weather?.firstOrNull()?.description ?: "",
                        unite = repo.getTempUnit(),
                        country = weather.body()?.main?.feelsLike.toString(),
                        lang = lang
                    )

                    // Save the FavoriteWeather object
                    repo.addFavoriteWeather(favoriteWeather)

                    // Create a Weather object from the fetched data
                    val currentWeather = Weather(
                        visibility = weather.body()?.visibility ?: 0,
                        timezone = weather.body()?.timezone ?: 0,
                        main = weather.body()?.main ?: Main(weather.body()?.main?.temp?: 0.0 , weather.body()?.main?.tempMin?: 0.0,
                            weather.body()?.main?.grndLevel?: 0,
                            weather.body()?.main?.humidity ?: 0, weather.body()?.main?.pressure?: 0,
                            weather.body()?.main?.seaLevel?: 0, weather.body()?.main?.feelsLike?: 0.0,
                            weather.body()?.main?.tempMax?: 0.0),
                        clouds = weather.body()?.clouds ?: Clouds(0),
                        sys = weather.body()?.sys ?: Sys(0, 0),
                        dt = weather.body()?.dt ?: 0,
                        coord = weather.body()?.coord ?: Coord(0.0, 0.0),
                        weather = weather.body()?.weather ?: emptyList(),
                        name = weather.body()?.name ?: "Unknown",
                        cod = weather.body()?.cod ?: 0,
                        id = weather.body()?.id ?: 0,
                        base = weather.body()?.base ?: "Unknown",
                        wind = weather.body()?.wind ?: Wind(0, 0.0, 0.0)
                    )

                    // Save the Weather object to Shared Preferences
                    repo.saveWeather(currentWeather)

                    Log.i(TAG, "Added to favorites: $favoriteWeather")
                    // Update the state with the successful API response
                    _currentWeatherStateFlow.value = ApiState.SuccessCurrent(weather)
                }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getHourlyWeather(lat: Double, lon: Double, lang: String, unit: String) {
        Log.i(TAG, "getHourlyWeather: ")
        viewModelScope.launch {
            repo.fetchHourlyForecast(lat, lon, lang, unit)
                .onStart {
                    _hourlyWeatherStateFlow.value = ApiState.Loading
                    _dailyWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _hourlyWeatherStateFlow.value = ApiState.Failure(e)
                }.collect { weather ->
                    val filterHourlyWeatherList = weather.filter { weatherForecast ->
                        areSameDay(weatherForecast.dt_txt, getCurrentDateTime())
                    }
                    val filterDailyWeatherList = weather.filter { weatherForecast ->
                        isMidnight(weatherForecast.dt_txt)
                    }

                    // Save the lists to Shared Preferences
                    repo.saveHourlyWeather(filterHourlyWeatherList)
                    repo.saveDailyWeather(filterDailyWeatherList)

                    // Update state flows with filtered results
                    _hourlyWeatherStateFlow.value = ApiState.SuccessForecast(filterHourlyWeatherList)
                    _dailyWeatherStateFlow.value = ApiState.SuccessForecast(filterDailyWeatherList)

                    Log.i(TAG, "getHourlyWeather: ${weather.size} ${filterDailyWeatherList}")
                }
        }
    }


    /*fun getDailyWeather(lat: Double, lon: Double,lang : String) {
        Log.i(TAG, "get Daily Weather: ")
        viewModelScope.launch{
            repo.fetchDailyForecast(lat,lon,lang)
                .onStart {
                    _dailyWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _dailyWeatherStateFlow.value = ApiState.Failure(e)
                }.collect{weather->
                    Log.i(TAG, "get Daily Weather in view model: ${weather.body()}")
                    _dailyWeatherStateFlow.value = ApiState.SuccessCurrent(weather)
                }


        }
    }*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun areSameDay(date1: String, date2:String):Boolean{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime1 = LocalDateTime.parse(date1,formatter)
        val dateTime2 = LocalDateTime.parse(date2,formatter)
        return dateTime1.toLocalDate() == dateTime2.toLocalDate()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
        Log.i("TAG", "getCurrentDateTime: ")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun isMidnight(dateTimeString: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)

        // Check if the time is exactly 00:00:00
        return dateTime.toLocalTime().toString() == "00:00"
    }
    fun getFavorites() {
        viewModelScope.launch {
            repo.getAllFavorites().collect { favorites ->
                Log.i(TAG, "Favorites from database: $favorites") // Log لعرض المفضلين
            }
        }
    }

    }