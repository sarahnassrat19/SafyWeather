package com.example.safyweather.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast

class LocalSource(context: Context):LocalSourceInterface {

    private var addressDao:FavoriteAddressDAO
    private var weatherDao:WeatherDAO

    init {
        val db = WeatherDatabase.getInstance(context.applicationContext)
        addressDao = db.addressesDao()
        weatherDao = db.weatherDao()
    }

    companion object{
        private var localSource:LocalSource? = null
        fun getInstance(context: Context):LocalSource{
            if(localSource == null){
                localSource = LocalSource(context)
            }
            return localSource!!
        }
    }

    override fun getAllAddresses(): LiveData<List<WeatherAddress>> {
        return addressDao.getAllAddresses()
    }

    override fun insertFavoriteAddress(address: WeatherAddress) {
        addressDao.insertFavoriteAddress(address)
    }

    override fun deleteFavoriteAddress(address: WeatherAddress) {
        addressDao.deleteFavoriteAddress(address)
    }

    override fun getWeatherWithLatLong(lat: Double, long: Double): LiveData<WeatherForecast> {
        return weatherDao.getWeatherWithLatLong(lat,long)
    }

    override fun insertWeather(weather: WeatherForecast) {
        weatherDao.insertWeather(weather)
    }

    override fun deleteWeather(weather: WeatherForecast) {
        weatherDao.deleteWeather(weather)
    }
}