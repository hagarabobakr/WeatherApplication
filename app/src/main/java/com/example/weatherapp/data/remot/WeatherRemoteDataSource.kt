package com.example.weatherapp.data.remot

import android.util.Log
import com.example.weatherapp.API_KEY
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.model.Wind
import com.example.weatherapp.units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class WeatherRemoteDataSource : IWeatherRemoteDataSourceImp {

    //val API_KEY = "f48d1fc66b3c4b9f11c2cbfbbe1047dc"
    private val WeatherService: ApiService by lazy {
        RetrofitHelper.getInstance().create(ApiService::class.java)
    }

    companion object {
        @Volatile
        private var instance: WeatherRemoteDataSource? = null

        fun getInstance(): WeatherRemoteDataSource {
            return instance ?: synchronized(this) {
                val tempInstance = instance ?: WeatherRemoteDataSource().also { instance = it }
                tempInstance
            }
        }
    }

    override suspend fun getCurrentWeatherBasic(lat: Double, lon: Double): List<Weather>? {
        val response = WeatherService.getCurrentWeatherBasic(lat, lon, API_KEY)
        return if (response.isSuccessful) {
            response.body()?.weather
        } else {
            Log.i("TAG", "getCurrentWeatherBasic: onFailure")
            null
        }
    }

    override suspend fun fetchCurrentWeather(lat: Double, lon: Double, lang: String):
            Flow<Response<Weather>> = flow {
        val response = WeatherService.getCurrentWeather(lat, lon, lang, API_KEY, units)
        if (response.isSuccessful && response.body() != null)
            emit(response)
    }.catch { e ->
        throw e
    }

    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String
    ): Flow<Response<Weather>> = flow {
        val response = WeatherService.getHourlyForecast(lat, lon, lang, API_KEY, units)
        if (response.isSuccessful && response.body() != null)
            emit(response)
    }.catch { e ->
        throw e
    }

    override suspend fun fetchDailyForecast(
        lat: Double,
        lon: Double,
        lang: String
    ): Flow<Response<Weather>> = flow {
        val response = WeatherService.getDailyForecast(lat, lon, lang, 8,API_KEY, units)
        if (response.isSuccessful && response.body() != null)
            emit(response)
    }.catch { e ->
        throw e
    }
}


/*override fun getProductsOverNetwork(): Flow<List<Product>> = flow {
         val response = productService.getProducts().products
        emit(response)
    }.catch { e ->
        throw e
    }*/


