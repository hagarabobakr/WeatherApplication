package com.example.weatherapp.view

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModel
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private  val TAG = "HomeFragment"
    private val remoteDataSource = WeatherRemoteDataSource()
    private val localDataSource = WeatherLocalDataSource()
    private val sharedPreferenceDataSourceImp =
        GlobalSharedPreferenceDataSourceImp(requireActivity().getSharedPreferences("MySharedPrefs",
            Context.MODE_PRIVATE))
    private val repository = WeatherRepository.getInstance(remoteDataSource,localDataSource,sharedPreferenceDataSourceImp)
    var lattitudeValue : Double = 0.0
    var longituteValue : Double =0.0
    lateinit var geoCoder : Geocoder
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var homeFactory: HomeFragmentViewModelFactory
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFactory = HomeFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this,homeFactory).get(HomeFragmentViewModel::class.java)
        viewModel.getCurrentWeather2(55.7522, 37.6156,"")
        viewModel.getCurrentWeather(10.99,44.34)
        viewModel.weather.observe(viewLifecycleOwner){ desc->
            binding.weatherDesc.text = desc?.get(0)?.description
          //  binding.cityName.text =
        }
        viewModel.weather2.observe(viewLifecycleOwner){w->
            binding.cityName.text = w?.name
            val dateInMillis = w?.dt?.times(1000) ?: 0 // Convert from seconds to milliseconds
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(dateInMillis))
            binding.date.text = formattedDate
            val tempInCelsius = w?.main?.temp?.minus(273.15)
            val feelsLikeInCelsius = w?.main?.feels_like?.minus(273.15)

            binding.temp.text = tempInCelsius?.let { String.format(" %.2f°C", it) } ?: "Temperature: N/A"
            binding.feelsLike.text = feelsLikeInCelsius?.let { String.format("Feels like: %.2f°C", it) } ?: "Feels like: N/A"
        // binding.toNight.text = w?.


            }

    }


}