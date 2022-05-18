package com.example.safyweather.db

import androidx.lifecycle.LiveData
import com.example.safyweather.model.AlertData
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast

interface LocalSourceInterface {

    //weather addresses funs
    fun getAllAddresses(): LiveData<List<WeatherAddress>>

    fun insertFavoriteAddress(address: WeatherAddress)

    fun deleteFavoriteAddress(address: WeatherAddress)

    //weather funs
    fun getAllStoredWeathers():LiveData<List<WeatherForecast>>

    fun getWeatherWithLatLong(lat:Double,long:Double):LiveData<WeatherForecast>

    fun insertWeather(weather: WeatherForecast)

    fun deleteWeather(weather: WeatherForecast)

    //alerts funs
    fun getAllStoredAlerts():LiveData<List<AlertData>>

    fun insertAlert(alert:AlertData)

    fun deleteAlert(alert:AlertData)
}