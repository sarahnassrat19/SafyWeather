package com.example.safyweather.favoritescreen.view

import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast

interface OnFavWeatherClickListener {
    fun onRemoveBtnClick(address: WeatherAddress,weather:WeatherForecast)
    fun onFavItemClick(address: WeatherAddress)
}