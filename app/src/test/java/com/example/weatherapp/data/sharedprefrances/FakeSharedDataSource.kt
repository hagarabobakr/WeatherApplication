package com.example.weatherapp.data.sharedprefrances

class FakeSharedDataSource:GlobalSharedPreferenceDataSource {

    private var unit: String = "metric"
    private var tempUnit: String = "C"
    override fun getUnit(): String {
        return unit
    }

    override fun setUnit(string: String) {
        unit = string
    }

    override fun getTempUnit(): String {
        return tempUnit
    }

    override fun setTempUnit(string: String) {
        tempUnit = string
    }

    override fun getMapLon(): String {
        TODO("Not yet implemented")
    }

    override fun setMapLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getMapLat(): String {
        TODO("Not yet implemented")
    }

    override fun setMapLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getGpsLon(): String {
        TODO("Not yet implemented")
    }

    override fun setGpsLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getGpsLat(): String {
        TODO("Not yet implemented")
    }

    override fun setGpsLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getFavLon(): String {
        TODO("Not yet implemented")
    }

    override fun setFavLon(string: String) {
        TODO("Not yet implemented")
    }

    override fun getFavLat(): String {
        TODO("Not yet implemented")
    }

    override fun setFavLat(string: String) {
        TODO("Not yet implemented")
    }

    override fun getLang(): String {
        TODO("Not yet implemented")
    }

    override fun setLang(string: String) {
        TODO("Not yet implemented")
    }

    override fun getWindSpeedUnit(): String {
        TODO("Not yet implemented")
    }

    override fun setWindSpeedUnit(string: String) {
        TODO("Not yet implemented")
    }

    override fun setNotificationsEnabled(string: String) {
        TODO("Not yet implemented")
    }

    override fun getNotificationsEnabled(): String {
        TODO("Not yet implemented")
    }

    override fun setLocationEnabled(string: String) {
        TODO("Not yet implemented")
    }

    override fun getLocationEnabled(): String {
        TODO("Not yet implemented")
    }
}