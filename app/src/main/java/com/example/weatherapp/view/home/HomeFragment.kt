package com.example.weatherapp.view.home

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiService
import com.example.weatherapp.data.remot.ApiState
import com.example.weatherapp.data.remot.RetrofitHelper
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModel
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var homeFactory: HomeFragmentViewModelFactory
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    private lateinit var dailyWeatherAdapter: DailyWeatherAdapter
    lateinit var mapAnimation : LottieAnimationView
    private  val TAG = "HomeFragment"
    private var city: String = ""
    private var dataFetched: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapAnimation = binding.mapAnimator
        mapAnimation.loop(true)
        mapAnimation.playAnimation()
        setupViewModel()
        setupRecyclerView()
        setUpHourlyWeatherObserver()
        setUpDailyWeatherObserver()
        // Call the setup method after initializing the ViewModel
        setUpCurrentWeatherObserver()
        binding.mapAnimator.setOnClickListener {
            val bundle = Bundle().apply {
                putString("sourceFragment", "HomeFragment")
            }
            findNavController().navigate(R.id.action_homeFragment2_to_mapFragment, bundle)
        }

    }

    private fun setupRecyclerView() {
        //HourlyWeatherAdapter
        hourlyWeatherAdapter = HourlyWeatherAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = hourlyWeatherAdapter

        //DailyWeatherAdapter
        dailyWeatherAdapter= DailyWeatherAdapter(viewModel.repo.getLang())
        binding.recyclerView2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView2.adapter = dailyWeatherAdapter
    }
    private fun setupViewModel() {
        val sharedPrefs = requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource(RetrofitHelper.getInstance().create(
            ApiService::class.java))
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        homeFactory = HomeFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, homeFactory).get(HomeFragmentViewModel::class.java)
    }


    private fun isNetworkAvailable(): Boolean {
        // Check for network connectivity
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpCurrentWeatherObserver() {
        Log.i(TAG, "setUpCurrentWeatherObserver: ")

        if (isNetworkAvailable()) {
            lifecycleScope.launch {
                viewModel.currentWeatherStateFlow.collectLatest { state ->
                    when (state) {
                        is ApiState.Loading -> {
                            // Show loading indicator
                            binding.loadingIndicator.visibility = View.VISIBLE
                        }
                        is ApiState.SuccessCurrent -> {
                            // Hide loading indicator and display weather
                            binding.loadingIndicator.visibility = View.GONE
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())

                            try {
                                val addressList = geocoder.getFromLocation(viewModel.repo.getGpsLat().toDouble(),
                                    viewModel.repo.getGpsLon().toDouble(), 1)
                                if (!addressList.isNullOrEmpty()) {
                                    val address = addressList[0]
                                    city = "${address.adminArea}, ${address.countryName}"
                                } else {
                                    Toast.makeText(requireContext(), R.string.Invalid, Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            binding.cityName.text = "${city}\n ${state.data.body()?.name}"
                            val dateInMillis = state.data.body()?.dt?.times(1000) ?: 0
                            val selectedLanguage = viewModel.repo.getLang()
                            val locale = if (selectedLanguage == "ar") Locale("ar") else Locale("en")
                            val dateFormat = SimpleDateFormat("dd MMM yyyy : HH:mm ", locale)
                            val formattedDate = dateFormat.format(Date(dateInMillis))
                            binding.date.text = formattedDate
                            binding.temp.text = "${state.data.body()?.main?.temp.toString()} ${viewModel.repo.getTempUnit()}"
                            binding.weatherDesc.text = state.data.body()?.weather?.get(0)?.description
                            binding.pressureTxt.text = "${state.data.body()?.main?.pressure.toString()} hpa"
                            binding.humidityTxt.text = "${state.data.body()?.main?.humidity.toString()} %"
                            binding.windTxt.text = "${state.data.body()?.wind?.speed.toString()} ${viewModel.repo.getWindSpeedUnit()}"
                            binding.cloudTxt.text = "${state.data.body()?.clouds?.all.toString()} %"
                            Glide.with(binding.image1.context)
                                .load("https://openweathermap.org/img/wn/${state.data.body()?.weather?.get(0)?.icon}.png")
                                .into(binding.image1)
                            Glide.with(binding.tempImg.context)
                                .load("https://openweathermap.org/img/wn/${state.data.body()?.weather?.get(0)?.icon}.png")
                                .into(binding.tempImg)
                        }
                        is ApiState.Failure -> {
                            // Hide loading indicator and show error
                            binding.loadingIndicator.visibility = View.GONE
                            Toast.makeText(requireContext(), getString(R.string.failed_to_load_weather_please_try_again), Toast.LENGTH_SHORT).show()
                            Log.e(TAG, getString(R.string.error_loading, state.msg))
                        }
                        else -> {}
                    }
                }
            }
        } else {
            // Fetch stored data if there's no internet connection
            val storedWeather = viewModel.repo.getWeather()
            if (storedWeather != null) {
                binding.cityName.text = "${storedWeather.name}\n ${storedWeather.name}"
                binding.temp.text = "${storedWeather.main.temp} ${viewModel.repo.getTempUnit()}"
                binding.weatherDesc.text = storedWeather.weather.firstOrNull()?.description ?: "No description"
                binding.pressureTxt.text = "${storedWeather.main.pressure} hPa"
                binding.humidityTxt.text = "${storedWeather.main.humidity} %"
                binding.windTxt.text = "${storedWeather.wind.speed} ${viewModel.repo.getWindSpeedUnit()}"
                binding.cloudTxt.text = "${storedWeather.clouds.all} %"
                val dateFormat = SimpleDateFormat("dd MMM yyyy : HH:mm", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(storedWeather.dt * 1000))
                binding.date.text = formattedDate
                Glide.with(binding.image1.context)
                    .load("https://openweathermap.org/img/wn/${storedWeather.weather.firstOrNull()?.icon}.png")
                    .into(binding.image1)

                Glide.with(binding.tempImg.context)
                    .load("https://openweathermap.org/img/wn/${storedWeather.weather.firstOrNull()?.icon}.png")
                    .into(binding.tempImg)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_weather_data_available), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi(weather:Weather){
        //binding.feelsLike.text = weather.main.feelsLike.toString()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpHourlyWeatherObserver() {
        if (isNetworkAvailable()) {
            lifecycleScope.launch {
                viewModel.hourlyWeatherStateFlow.collectLatest { state ->
                    when (state) {
                        is ApiState.Loading -> {
                            // Optionally show a loading indicator for hourly weather
                            Log.i(TAG, "setUpHourlyWeatherObserver Loading weather...")
                        }
                        is ApiState.SuccessForecast -> {
                            // Update hourly weather data
                            hourlyWeatherAdapter.submitList(state.data.toList())
                            Log.i(TAG, "setUpHourlyWeatherObserver SuccessForecast: ${state.data.size}")
                        }
                        is ApiState.Failure -> {
                            Toast.makeText(requireContext(), getString(R.string.failed_to_load_hourly_weather), Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        } else {
            // Fetch stored hourly weather data
            val storedHourlyWeather = viewModel.repo.getHourlyWeather()
            if (storedHourlyWeather != null) {
                hourlyWeatherAdapter.submitList(storedHourlyWeather)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_hourly_weather_data_available), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpDailyWeatherObserver() {
        if (isNetworkAvailable()) {
            lifecycleScope.launch {
                viewModel.dailyWeatherStateFlow.collectLatest { state ->
                    when (state) {
                        is ApiState.Loading -> {
                            // Optionally show a loading indicator for daily weather
                            Log.i(TAG, "setUp Daily Weather Observer Loading weather...")
                        }
                        is ApiState.SuccessForecast -> {
                            // Update daily weather data
                            dailyWeatherAdapter.submitList(state.data.toList())
                        }
                        is ApiState.Failure -> {
                            Toast.makeText(requireContext(), getString(R.string.failed_to_load_daily_weather), Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        } else {
            // Fetch stored daily weather data if there's no internet connection
            val storedDailyWeather = viewModel.repo.getDailyWeather()
            if (storedDailyWeather != null) {
                dailyWeatherAdapter.submitList(storedDailyWeather)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_daily_weather_data_available), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
