package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherapp.R
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.ActivitySplashBinding
import com.example.weatherapp.viewmodel.splash.SplashViewModel
import com.example.weatherapp.viewmodel.splash.SplashViewModelFactory
import mumayank.com.airlocationlibrary.AirLocation
import java.util.Locale

class SplashActivity : AppCompatActivity(), AirLocation.Callback  {
    private  val TAG = "SplashActivity"
   private lateinit var binding : ActivitySplashBinding
    lateinit var splashAnimation : LottieAnimationView
   private lateinit var viewModel : SplashViewModel
   private lateinit var splashViewModelFactory: SplashViewModelFactory
   lateinit var airLocation : AirLocation
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
        Handler(Looper.getMainLooper()).postDelayed({
            getLocation()
        }, 3000)

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
            viewModel.saveLocation(lat,lon)
            val geocoder = Geocoder(this, Locale.getDefault())
            //Log.d("Splash", "lat: $lat, lon: $lon")
            try {
                val addressList = geocoder.getFromLocation(lat, lon, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    city = "${address.adminArea}, ${address.countryName}"
                    dataFetched = true
                    navigateToMainActivity()
                } else {
                    // Address list is empty or null
                    Toast.makeText(this, R.string.Invalid, Toast.LENGTH_SHORT).show()
                    //binding.textSplash.text = getString(R.string.Location_Unknown)
                }
            } catch (e: Exception) {

                e.printStackTrace()
               // Toast.makeText(this, R.string.geocoder_service_not_available, Toast.LENGTH_SHORT).show()
                //binding.textSplash.text = getString(R.string.Location_Unknown)
            }
        }

    }
    private fun setupViewModel() {
        val sharedPrefs = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource()
        val localDataSource = WeatherLocalDataSource()
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        val splashViewModelFactory = SplashViewModelFactory(repository)
         viewModel = ViewModelProvider(this, splashViewModelFactory).get(SplashViewModel::class.java)

    }

    private fun navigateToMainActivity() {
        Intent(this, HomeActivity::class.java).also {
            it.putExtra("latitude", lat)
            it.putExtra("longitude", lon)
            startActivity(it)
            finish()
        }
}
}