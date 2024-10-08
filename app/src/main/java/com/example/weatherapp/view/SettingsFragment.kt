package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiService
import com.example.weatherapp.data.remot.RetrofitHelper
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModel
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModelFactory
import com.example.weatherapp.viewmodel.settings.SettingsViewModel
import com.example.weatherapp.viewmodel.settings.SettingsViewModelFactory
import java.util.Locale


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private lateinit var settingsFactory: SettingsViewModelFactory
    private  val TAG = "SettingsFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()


        /*val sharedPrefs = requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource()
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        settingsFactory = SettingsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, settingsFactory).get(SettingsViewModel::class.java)*/

        setInitialValues()
        binding.arrowLanguageIcon.setOnClickListener {
            toggleVisibility(binding.expandableLanguage)
        }

        binding.arrowTempIcon.setOnClickListener {
            toggleVisibility(binding.expandableTemp)
        }

        binding.arrowUnitIcon.setOnClickListener {
            toggleVisibility(binding.expandableUnit)
        }
        binding.arrowIcon.setOnClickListener{
            toggleVisibility(binding.expandableNotifications)
        }
        binding.arrowLocationIcon.setOnClickListener {
            toggleVisibility(binding.expandableLocation)
        }

        //  RadioButtons

        //radioGroupLanguage
        binding.radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
            val language = when (checkedId) {
                binding.radioArabic.id -> "ar"
                binding.radioEnglish.id -> "en"
                else -> ""
            }
            viewModel.selectLanguage(language)
            changeAppLanguage(language)
        }

        //radioGroupTemp
        binding.radioGroupTemp.setOnCheckedChangeListener { _, checkedId ->
            val unit = when (checkedId) {
                binding.radioKelvin.id -> "K"
                binding.radioCelsius.id -> "C"
                binding.radioFahrenheit.id -> "F"
                else -> ""
            }
            viewModel.selectTemperatureUnit(unit)
        }

        //radioGroupWind
        binding.radioGroupWind.setOnCheckedChangeListener { _, checkedId ->
            val unit = when (checkedId) {
                binding.radioMeterSec.id -> "M/S"
                binding.radioKmh.id -> "K/H"
                binding.radioMph.id -> "M/H"
                else -> ""
            }
            viewModel.selectWindUnit(unit)
        }

        //radioGroupLocation
        binding.radioGroupLocation.setOnCheckedChangeListener { _, checkedId ->
            val location = when (checkedId) {
                binding.radioMap.id ->{
                    viewModel.selectLocationEnabled("Map")
                    performActionForMap()
                    "Map"
                }
                binding.radioGps.id ->{
                    viewModel.selectLocationEnabled("GPS")
                    performActionForGPS()
                    "GPS"
                }
                else -> ""
            }
            viewModel.selectLocationEnabled(location)
        }

        //radioGroupNotifications
        binding.radioGroupNotifications.setOnCheckedChangeListener { _, checkedId ->
            val notifications = when (checkedId) {
                binding.radioEnable.id -> "Enable"
                binding.radioDisable.id -> "Disable"
                else -> ""
            }
            viewModel.selectNotificationsEnabled(notifications)
        }

        /*binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.selectNotificationsEnabled(isChecked)
        }*/

        /*binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            viewModel.selectLocationEnabled(isChecked)
        }*/

        viewModel.selectedLanguage.observe(viewLifecycleOwner) {
        }
        viewModel.selectedTemperatureUnit.observe(viewLifecycleOwner) {
        }
        viewModel.selectedWindUnit.observe(viewLifecycleOwner) {
        }
        viewModel.locationEnabled.observe(viewLifecycleOwner) {
        }

        viewModel.notificationsEnabled.observe(viewLifecycleOwner) {

        }

    }

    private fun setupViewModel() {
        val sharedPrefs = requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource(RetrofitHelper.getInstance().create(
            ApiService::class.java))
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        settingsFactory = SettingsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, settingsFactory).get(SettingsViewModel::class.java)
    }
    private fun changeAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun performActionForGPS() {

    }

    private fun performActionForMap() {
        val bundle = Bundle().apply {
        putString("sourceFragment", "SettingsFragment")
    }
        findNavController().navigate(R.id.action_settingsFragment2_to_mapFragment, bundle)
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
    private fun setInitialValues() {
        // Retrieve the selected language
        val language = viewModel.repo.getLang()
        binding.radioGroupLanguage.check(
            when (language) {
                "ar" -> binding.radioArabic.id // If the language is Arabic
                "en" -> binding.radioEnglish.id // If the language is English
                else -> -1 // Unknown value
            }
        )

        // Retrieve the temperature unit
        val tempUnit = viewModel.repo.getTempUnit()
        binding.radioGroupTemp.check(
            when (tempUnit) {
                "Kelvin" -> binding.radioKelvin.id // If the temperature unit is Kelvin
                "Celsius" -> binding.radioCelsius.id // If the temperature unit is Celsius
                "Fahrenheit" -> binding.radioFahrenheit.id // If the temperature unit is Fahrenheit
                else -> -1 // Unknown value
            }
        )

        // Retrieve the wind speed unit
        val windUnit = viewModel.repo.getWindSpeedUnit()
        binding.radioGroupWind.check(
            when (windUnit) {
                "Meter/Second" -> binding.radioMeterSec.id // If the wind speed unit is Meter/Second
                "Km/Hour" -> binding.radioKmh.id // If the wind speed unit is Km/Hour
                "Miles/Hour" -> binding.radioMph.id // If the wind speed unit is Miles/Hour
                else -> -1 // Unknown value
            }
        )

        // Retrieve location settings
        val locationEnabled = viewModel.repo.getMapLon()
        binding.radioGroupLocation.check(
            when (locationEnabled) {
                "GPS" -> binding.radioGps.id // If location setting is GPS
                "Map" -> binding.radioMap.id // If location setting is Map
                else -> -1 // Unknown value
            }
        )

        // Retrieve notification settings
        val notificationsEnabled = viewModel.repo.getNotificationsEnabled()
        binding.radioGroupNotifications.check(
            when (notificationsEnabled) {
                "Enable" -> binding.radioEnable.id // If notifications are enabled
                "Disable" -> binding.radioDisable.id // If notifications are disabled
                else -> -1 // Unknown value
            }
        )
    }


}