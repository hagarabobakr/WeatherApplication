package com.example.weatherapp.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.WeatherRepository

class SettingsViewModel(val repo: WeatherRepository) : ViewModel() {
    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    private val _selectedTemperatureUnit = MutableLiveData<String>()
    val selectedTemperatureUnit: LiveData<String> get() = _selectedTemperatureUnit

    private val _selectedWindUnit = MutableLiveData<String>()
    val selectedWindUnit: LiveData<String> get() = _selectedWindUnit

    private val _notificationsEnabled = MutableLiveData<String>()
    val notificationsEnabled: LiveData<String> get() = _notificationsEnabled

    private val _locationEnabled = MutableLiveData<String>()
    val locationEnabled: LiveData<String> get() = _locationEnabled

    fun selectLanguage(language: String) {
        _selectedLanguage.value = language
        repo.setLang(language)
    }

    fun selectTemperatureUnit(unit: String) {
        _selectedTemperatureUnit.value = unit
        repo.setTempUnit(unit)
    }

    fun selectWindUnit(unit: String) {
        _selectedWindUnit.value = unit
        repo.setWindSpeedUnit(unit)
    }

    fun selectNotificationsEnabled(enabled: String) {
        _notificationsEnabled.value = enabled
        repo.setNotificationsEnabled(enabled)
    }

    fun selectLocationEnabled(location: String) {
        _locationEnabled.value = location
        repo.setLocationEnabled(location)
    }
}

