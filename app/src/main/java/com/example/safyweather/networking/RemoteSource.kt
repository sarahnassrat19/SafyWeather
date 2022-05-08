package com.example.safyweather.networking

import android.util.Log
import com.example.safyweather.model.CurrentWeather
import com.example.safyweather.model.WeatherForecast

class RemoteSource :RemoteSourceInterface{
    override suspend fun getCurrentWeatherWithLocation(lat:Double,long:Double,unit:String): WeatherForecast {
        //var result:WeatherForecast
        val apiService = RetrofitHelper.getInstance().create(API_Interface::class.java)
        Log.i("TAG", "getCurrentWeatherWithLocation: in Remooooote source apiService")

        val response = apiService.getTheWholeWeather(lat,long,unit,"minutely","375d11598481406538e244d548560243")
        Log.i("TAG", "getCurrentWeatherWithLocation: in Remooooote source response")
        if(response.isSuccessful){
            //result = response.body() as WeatherForecast
            Log.i("TAG", "successsssssssssssssssssssssssss response")
        }
        else{
            Log.i("TAG", "faillllllllllllllllllllllllllled response")
        }
        return response.body() as WeatherForecast
    }

    companion object{
        private var instance: RemoteSource? = null
        fun getInstance(): RemoteSource{
            return instance?: RemoteSource()
        }
    }
}