package com.example.safyweather.networking

import com.example.safyweather.model.WeatherForecast

interface RemoteSourceInterface {
    suspend fun getCurrentWeatherWithLocation():WeatherForecast
}