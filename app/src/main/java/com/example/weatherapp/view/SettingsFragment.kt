package com.example.weatherapp.view

import android.content.Context
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
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModel
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModelFactory
import com.example.weatherapp.viewmodel.settings.SettingsViewModel
import com.example.weatherapp.viewmodel.settings.SettingsViewModelFactory


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

        val sharedPrefs = requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource()
        val localDataSource = WeatherLocalDataSource()
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)
        settingsFactory = SettingsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, settingsFactory).get(SettingsViewModel::class.java)

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
        }

        //radioGroupTemp
        binding.radioGroupTemp.setOnCheckedChangeListener { _, checkedId ->
            val unit = when (checkedId) {
                binding.radioKelvin.id -> "Kelvin"
                binding.radioCelsius.id -> "Celsius"
                binding.radioFahrenheit.id -> "Fahrenheit"
                else -> ""
            }
            viewModel.selectTemperatureUnit(unit)
        }

        //radioGroupWind
        binding.radioGroupWind.setOnCheckedChangeListener { _, checkedId ->
            val unit = when (checkedId) {
                binding.radioMeterSec.id -> "Meter/Second"
                binding.radioKmh.id -> "Km/Hour"
                binding.radioMph.id -> "Miles/Hour"
                else -> ""
            }
            viewModel.selectWindUnit(unit)
        }

        //radioGroupLocation
        binding.radioGroupLocation.setOnCheckedChangeListener { _, checkedId ->
            val location = when (checkedId) {
                binding.radioMph.id -> "Map"
                binding.radioGps.id -> "GPS"
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

        // مراقبة القيم في ViewModel
        viewModel.selectedLanguage.observe(viewLifecycleOwner) {
            // تحديث واجهة المستخدم إذا لزم الأمر
        }

        viewModel.selectedTemperatureUnit.observe(viewLifecycleOwner) {
            // تحديث واجهة المستخدم إذا لزم الأمر
        }

        viewModel.selectedWindUnit.observe(viewLifecycleOwner) {
            // تحديث واجهة المستخدم إذا لزم الأمر
        }
        viewModel.locationEnabled.observe(viewLifecycleOwner) {

        }

        viewModel.notificationsEnabled.observe(viewLifecycleOwner) {

        }

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
}