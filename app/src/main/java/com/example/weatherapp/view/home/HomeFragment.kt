package com.example.weatherapp.view.home

import android.content.Context
import android.location.Geocoder
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
import com.example.weatherapp.data.remot.ApiState
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
        val remoteDataSource = WeatherRemoteDataSource()
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        homeFactory = HomeFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, homeFactory).get(HomeFragmentViewModel::class.java)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpCurrentWeatherObserver(){
        Log.i(TAG, "setUpCurrentWeatherObserver: ")
        lifecycleScope.launch {
            viewModel.currentWeatherStateFlow.collectLatest{state->
                when(state){
                    is ApiState.Loading -> {
                        // Show loading indicator
                        binding.loadingIndicator.visibility = View.VISIBLE
                        Log.i(TAG, getString(R.string.loading_weather))
                    }
                    is ApiState.SuccessCurrent -> {
                        // Hide loading indicator and display weather
                        binding.loadingIndicator.visibility = View.GONE
                        //binding.feelsLike.text = state.data.body()?.main?.feelsLike.toString()
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())

                        try {
                            val addressList = geocoder.getFromLocation(viewModel.repo.getGpsLat().toDouble(),
                                viewModel.repo.getGpsLon().toDouble(), 1)
                            if (!addressList.isNullOrEmpty()) {
                                val address = addressList[0]
                                city = "${address.adminArea}, ${address.countryName}"
                            } else {
                                // Address list is empty or null
                                Toast.makeText(requireContext(), R.string.Invalid, Toast.LENGTH_SHORT).show()
                                //binding.textSplash.text = getString(R.string.Location_Unknown)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            // Toast.makeText(this, R.string.geocoder_service_not_available, Toast.LENGTH_SHORT).show()
                            //binding.textSplash.text = getString(R.string.Location_Unknown)
                        }
                        binding.cityName.text = "${city}\n ${state.data.body()?.name}"
                       // binding.date.text = state.data.body()?.dt.toString()
                        //date after formating
                        val dateInMillis =  state.data.body()?.dt?.times(1000) ?: 0 // Convert from seconds to milliseconds
                        val selectedLanguage = viewModel.repo.getLang()
                        val locale = if (selectedLanguage == "ar") Locale("ar") else Locale("en")
                        val dateFormat = SimpleDateFormat("dd MMM yyyy : HH:mm ", locale)
                        val formattedDate = dateFormat.format(Date(dateInMillis))
                        binding.date.text = formattedDate
                        binding.temp.text = "${state.data.body()?.main?.temp.toString()} ${viewModel.repo.getTempUnit()}"
                        binding.weatherDesc.text = state.data.body()?.weather?.get(0)?.description
                        binding.pressureTxt.text = "${state.data.body()?.main?.pressure.toString()} hpa"
                        binding.humidityTxt.text = "${state.data.body()?.main?.humidity.toString()} %"
                        binding.windTxt.text = "${state.data.body()?.wind?.speed .toString()}${viewModel.repo.getWindSpeedUnit()}"
                        binding.cloudTxt.text = "${state.data.body()?.clouds?.all.toString()} %"
                        Glide.with(binding.image1.context)
                            .load("https://openweathermap.org/img/wn/${state.data.body()?.weather?.get(0)?.icon}.png")
                            .into(binding.image1)
                        Glide.with(binding.tempImg.context)
                            .load("https://openweathermap.org/img/wn/${state.data.body()?.weather?.get(0)?.icon}.png")
                            .into(binding.tempImg)
                        Log.i(TAG, "setUpCurrentWeatherObserver: ${state.data.body()?.main?.feelsLike.toString()}")
                        Log.i(TAG, "setUpCurrentWeatherObserver name: ${state.data.body()?.name}")
                        Log.i(TAG, "setUpCurrentWeatherObserver: ${state.data.body()?.dt.toString()}")
                    }
                    is ApiState.Failure -> {
                        // Hide loading indicator and show error
                        binding.loadingIndicator.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.failed_to_load_weather_please_try_again),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e(TAG, getString(R.string.error_loading, state.msg))
                    }

                    else -> {}
                }

            }
        }
    }

    private fun updateUi(weather:Weather){
        //binding.feelsLike.text = weather.main.feelsLike.toString()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpHourlyWeatherObserver() {
        lifecycleScope.launch {
            viewModel.hourlyWeatherStateFlow.collectLatest { state ->
                when (state) {
                    is ApiState.Loading -> {
                        // Optionally show a loading indicator for hourly weather
                        Log.i(TAG, "setUpHourlyWeatherObserver Loading weather...")
                    }
                    is ApiState.SuccessForecast -> {
                        //val hourlyWeather = state.data.body()?
                        hourlyWeatherAdapter.submitList(state.data.toList())
                        Log.i(TAG, "setUpHourlyWeatherObserver SuccessForecast: ${state.data.size}")
                    }
                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.failed_to_load_hourly_weather), Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpDailyWeatherObserver() {
        lifecycleScope.launch {
            viewModel.dailyWeatherStateFlow.collectLatest { state ->
                when (state) {
                    is ApiState.Loading -> {
                        // Optionally show a loading indicator for hourly weather
                        Log.i(TAG, "setUp Daily Weather Observer Loading weather...")
                    }
                    is ApiState.SuccessForecast -> {
                        //val hourlyWeather = state.data.body()?
                        val dailyWeatherResponse = state.data.toList()
                        val weatherData = dailyWeatherResponse
                        dailyWeatherAdapter.submitList(weatherData)
                       // Log.i(TAG, "setUpDailyWeatherObserver SuccessForecast: ${state.data.size}")
                    }
                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.failed_to_load_daily_weather), Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }
}