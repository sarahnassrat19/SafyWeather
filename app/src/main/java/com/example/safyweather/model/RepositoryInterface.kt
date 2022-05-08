package com.example.safyweather.model

interface RepositoryInterface {
    suspend fun getCurrentWeatherWithLocationInRepo(lat:Double,long:Double,unit:String):WeatherForecast
}