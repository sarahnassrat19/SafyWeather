package com.example.safyweather.model

interface RepositoryInterface {
    suspend fun getCurrentWeatherWithLocationInRepo():WeatherForecast
}