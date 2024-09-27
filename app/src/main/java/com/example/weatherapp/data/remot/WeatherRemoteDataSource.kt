package com.example.weatherapp.data.remot

import android.annotation.SuppressLint
import android.util.Log
import com.example.weatherapp.API_KEY
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class WeatherRemoteDataSource : IWeatherRemoteDataSourceImp {
    private  val TAG = "WeatherRemoteDataSource"
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

//    override suspend fun getCurrentWeatherBasic(lat: Double, lon: Double): List<Weather>? {
//        val response = WeatherService.getCurrentWeatherBasic(lat, lon, API_KEY)
//        return if (response.isSuccessful) {
//            response.body()?.weather
//        } else {
//            Log.i("TAG", "getCurrentWeatherBasic: onFailure")
//            null
//        }
//    }

    override suspend fun fetchCurrentWeather(lat: Double, lon: Double, lang: String):
            Flow<Response<Weather>> = flow {
        val response = WeatherService.getCurrentWeather(lat, lon, lang, API_KEY, units)
        if (response.isSuccessful && response.body() != null)
            emit(response)
        Log.i(TAG, "fetchCurrentWeather: $response")
    }.catch { e ->
        throw e
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun fetchHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String
    ): Flow<List<WeatherForecast>> = flow {
        val response = WeatherService.getHourlyForecast(lat, lon, lang,units,API_KEY)
            emit(response.list)
        Log.i(TAG, "fetchHourlyForecast: $response")
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


