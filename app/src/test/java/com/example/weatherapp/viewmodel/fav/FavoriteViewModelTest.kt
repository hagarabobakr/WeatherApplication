package com.example.weatherapp.viewmodel.fav

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.data.model.FakeRepo
import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.getOrAwaitValue
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var Repository: FakeRepo
    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)
    @Before
    fun setUp() {

        Dispatchers.setMain(Dispatchers.Unconfined) // Set the Main dispatcher for tests
        Repository = FakeRepo()
        viewModel = FavoriteViewModel(Repository)
    }
  @Test
  @Config(manifest = Config.NONE)
    fun getFavorites() {
     viewModel.getFavorites()
      val favoritesList = viewModel.localWeather
      assertThat(favoritesList,not(emptyList<FavoriteWeather>()))
    }

    @Test
    fun removeFav() = testScope.runTest {
        val favoriteWeather = FavoriteWeather(
            name = "Test City",
            temp = 15.0,
            icon = "https://openweathermap.org/img/wn/test.png",
            dt = System.currentTimeMillis(),
            lon = 0.0,
            lat = 0.0,
            description = "Clear sky",
            unite = "Celsius",
            country = "Test Country",
            lang = "en"
        )
        Repository.addFavoriteWeather(favoriteWeather)
        viewModel.removeFav(favoriteWeather)
        viewModel.getFavorites()
        val favoritesListAfterRemoval = viewModel.localWeather.value
        assertFalse(favoritesListAfterRemoval?.contains(favoriteWeather) == true)
    }
}