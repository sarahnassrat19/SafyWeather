package com.example.safyweather.model

import androidx.lifecycle.LiveData

interface RepositoryInterface {
    suspend fun getCurrentWeatherWithLocationInRepo(lat:Double,long:Double,unit:String):WeatherForecast

    val storedAddresses:LiveData<List<WeatherAddress>>

    //val oneStoredWeather:LiveData<WeatherForecast>

    fun getOneWeather(lat:Double,long:Double):LiveData<WeatherForecast>

    fun insertFavoriteAddress(address: WeatherAddress)

    fun deleteFavoriteAddress(address: WeatherAddress)

    fun insertWeather(weather: WeatherForecast)

    fun deleteWeather(weather: WeatherForecast)

    //fun updateWeatherDatabase()


}