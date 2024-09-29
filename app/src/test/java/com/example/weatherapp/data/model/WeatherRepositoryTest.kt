package com.example.weatherapp.data.model

import com.example.weatherapp.data.local.FakeLocalDataSource
import com.example.weatherapp.data.remot.FakeRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.FakeSharedDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class WeatherRepositoryTest {
    val weather1 = FavoriteWeather("Cairo",27.0,"",20,30.0, 31.0, "windy","metric","egypt","en")
    val weather2 = FavoriteWeather("London",28.0,"",5,50.0, 65.0, "rainy","metric","paris","en")
    val localWeatherList = listOf(weather1, weather2)

    lateinit var fakeLocalDataSource: FakeLocalDataSource
    lateinit var fakeRemoteDataSource: FakeRemoteDataSource
    lateinit var weatherRepository: WeatherRepository
    private lateinit var fakeWeatherResponse: Response<Weather>
    val main =Main(10.0,20.5,5,6,99,100,10.3,30.5)
    val coord =Coord(10.5,20.5)
    val clouds =Clouds(100)
    val sys =Sys(20,80)
    val weatherList = listOf(WeatherElement("icon","description","main",1))
    val wind =Wind(10,20.6,60.8)


    @Before
    fun setup() {
        fakeLocalDataSource = FakeLocalDataSource(localWeatherList.toMutableList())
        fakeWeatherResponse = Response.success(
            Weather(
                visibility = 10,
                timezone = 20,
                main = main,
                clouds = clouds,
                sys = sys,
                dt = 20,
                coord = coord,
                weather = weatherList,
                name = "cairo",
                cod = 20,
                id = 2,
                base = "base",
                wind = wind
            )
        )

        fakeRemoteDataSource = FakeRemoteDataSource(fakeWeatherResponse)
        weatherRepository = WeatherRepository(fakeRemoteDataSource, fakeLocalDataSource, FakeSharedDataSource())
    }
    @Test
    fun getLocalWeathers_returnsLocalWeatherData() = runTest {
        // When: fetching local weathers
        val result = weatherRepository.getAllFavorites().toList()
        assertThat(result, `is`(listOf(localWeatherList)))

    }

    @Test
    fun insertWeather_addsWeatherToLocalDataSource() = runTest {
        // Given: a new weather model to insert
        val newWeather = FavoriteWeather("New York",27.0,"",20,30.0, 31.0, "windy","metric","egypt","en")

        // When: inserting the new weather
        weatherRepository.addFavoriteWeather(newWeather)

        // Then: the local data source should contain the new weather
        val result = weatherRepository.getAllFavorites().first()
        assertThat(result, hasItem(newWeather))
    }

    @Test
    fun getCurrentWeather_returnsWeatherData() = runTest {
        // When: fetching current weather
        val result = weatherRepository.fetchCurrentWeather (12.34, 56.78, "en", "metric").toList()

        // Then: assert that the emitted data matches the expected fake response
        assertThat(result[0].body(), `is`(fakeWeatherResponse.body()))
    }


}