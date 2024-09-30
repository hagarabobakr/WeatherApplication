package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherapp.R
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiService
import com.example.weatherapp.data.remot.RetrofitHelper
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.ActivitySplashBinding
import com.example.weatherapp.viewmodel.splash.SplashViewModel
import com.example.weatherapp.viewmodel.splash.SplashViewModelFactory
import mumayank.com.airlocationlibrary.AirLocation
import java.util.Locale

class SplashActivity : AppCompatActivity(), AirLocation.Callback  {
    private val TAG = "SplashActivity"
    private lateinit var binding: ActivitySplashBinding
    private lateinit var splashAnimation: LottieAnimationView
    private lateinit var viewModel: SplashViewModel
    private lateinit var splashViewModelFactory: SplashViewModelFactory
    private lateinit var airLocation: AirLocation
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var city: String = ""
    private var dataFetched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        splashAnimation = binding.weatherAnimator
        splashAnimation.loop(true)
        splashAnimation.playAnimation()

        // Check internet connection before fetching location
        if (isNetworkAvailable()) {
            Handler(Looper.getMainLooper()).postDelayed({
                getLocation()
            }, 3000)
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            // Optionally, you can navigate to main activity with stored data if needed
            navigateToMainActivity()
        }
    }

    private fun getLocation() {
        Log.i(TAG, "getLocation: ")
        airLocation = AirLocation(
            this,
            this,
            false,
            3,
            R.string.location_text.toString()
        )
        airLocation.start()
    }

    override fun onFailure(locationFailedEnum: AirLocation.LocationFailedEnum) {
        Toast.makeText(this, R.string.check_location, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(locations: ArrayList<Location>) {
        if (dataFetched) {
            Log.d("Splash", R.string.data_fetch.toString())
        } else {
            lat = locations[0].latitude
            lon = locations[0].longitude
            viewModel.saveLocation(lat, lon)
            val geocoder = Geocoder(this, Locale.getDefault())
            try {
                val addressList = geocoder.getFromLocation(lat, lon, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    city = "${address.adminArea}, ${address.countryName}"
                    dataFetched = true
                    navigateToMainActivity()
                } else {
                    Toast.makeText(this, R.string.Invalid, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupViewModel() {
        val sharedPrefs = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource(RetrofitHelper.getInstance().create(ApiService::class.java))
        val database = AppDatabase.getDatabase(this)
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        splashViewModelFactory = SplashViewModelFactory(repository)
        viewModel = ViewModelProvider(this, splashViewModelFactory).get(SplashViewModel::class.java)
    }

    private fun navigateToMainActivity() {
        splashAnimation.cancelAnimation()
        Intent(this, HomeActivity::class.java).also {
            it.putExtra("latitude", lat)
            it.putExtra("longitude", lon)
            startActivity(it)
            finish()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo?.isConnected == true
        }
}
}