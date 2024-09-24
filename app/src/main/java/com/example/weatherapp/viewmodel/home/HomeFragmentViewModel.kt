package com.example.weatherapp.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel (private val repo : WeatherRepository) : ViewModel() {
    private  val TAG = "HomeFragmentViewModel"
    var main : Main? = null
    //getCurrentWeather
    private val _weather = MutableLiveData<List<Weather>?>()
    val weather: LiveData<List<Weather>?> = _weather
    //getCityName
    private val _name = MutableLiveData<List<String>?>()
    val name: LiveData<List<String>?> = _name

    private val _weather2 = MutableLiveData<WeatherResponse?>()
    val weather2: LiveData<WeatherResponse?> = _weather2
    var w2 :WeatherResponse? = null

    init {
        //getCurrentWeather(10.99,44.34)
       // getCityName(10.99,44.34)
    }
    /*fun getCurrentWeather2(lat: Double, lon: Double,lang : String) {
        viewModelScope.launch(Dispatchers.IO){
             w2 = repo.getCurrentWeather(lat,lon,lang)
           // val name = repo.getCurrentWeather(lat,lon,lang)?.name
            withContext(Dispatchers.Main){
                _weather2.postValue(w2)
              //  _name.postValue(name?.let { listOf(it) })
                Log.i(TAG, "getCityName: ${_name.let { listOf(it) }}")
            }
        }

    }
    fun getCurrentWeather(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO){
            val w = repo.getCurrentWeatherBasic(lat,lon)
            withContext(Dispatchers.Main){
                _weather.postValue(w)
            }
        }
*/
    }

    /*fun getCityName(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO){
            val n = repo.getCityName(lat,lon)
            withContext(Dispatchers.Main){
                _name.postValue(n?.let { listOf(it) })
                Log.i(TAG, "getCityName: ${n?.let { listOf(it) }}")
            }
        }
    }*/
    /*fun getMain(lat: Double, lon: Double) : Main?{

        viewModelScope.launch {
            main =  repo.getMain(lat,lon)
        }
        return main
    }*/
