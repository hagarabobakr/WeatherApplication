package com.example.weatherapp.viewmodel.fav

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteViewModel(val repo: WeatherRepository):ViewModel() {
    private val TAG = "FavoriteViewModel"
    var lat = repo.getFavLat().toDouble()
    var lon = repo.getFavLon().toDouble()
    var  lang = repo.getLang()
    //get current weather
    private val _currentFavWeatherStateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val currentFavWeatherStateFlow: StateFlow<ApiState> = _currentFavWeatherStateFlow
    //save current weather
    private var _favWeatherList = MutableLiveData<List<FavoriteWeather>>()
    val localWeather: LiveData<List<FavoriteWeather>> = _favWeatherList

init {
    getCurrentWeather(lat,lon,lang)
    getFavorites()
}
    fun getCurrentWeather(lat: Double, lon: Double,lang : String) {
        Log.i(TAG, "getCurrentWeather: ")
        viewModelScope.launch{
            repo.fetchCurrentWeather(lat,lon,lang)
                .onStart {
                    _currentFavWeatherStateFlow.value = ApiState.Loading
                }.catch { e ->
                    _currentFavWeatherStateFlow.value = ApiState.Failure(e)
                }.collect{weather->
                    Log.i(TAG, "getCurrentWeather: ${weather.body()}")

                    val favoriteWeather = FavoriteWeather(
                        name = weather.body()?.name?:" ",
                        temp = weather.body()?.main?.temp?:23.0,
                        dt = weather.body()?.dt ?: 0,
                        icon = weather.body()?.weather?.get(0)?.icon?:" ",
                        lon = weather.body()?.coord?.lon,
                        lat = weather.body()?.coord?.lat,
                        description = weather.body()?.weather?.firstOrNull()?.description ?: "",
                        unite = repo.getTempUnit(),
                        country = weather.body()?.main?.feelsLike.toString(),
                        lang = lang
                    )
                    repo.addFavoriteWeather(favoriteWeather)
                    Log.i(TAG, "Added to favorites: $favoriteWeather")
                    _currentFavWeatherStateFlow.value = ApiState.SuccessCurrent(weather)
                }


        }
    }
    fun getFavorites() {
        viewModelScope.launch {
            repo.getAllFavorites().collect { favorites ->
                Log.i(TAG, "Favorites from database: $favorites")
                _favWeatherList.postValue(favorites)
            }
        }
    }
}