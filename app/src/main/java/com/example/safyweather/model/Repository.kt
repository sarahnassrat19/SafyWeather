package com.example.safyweather.model

import android.content.Context
import android.util.Log
import com.example.safyweather.networking.RemoteSourceInterface

class Repository(var remoteSource: RemoteSourceInterface,
                 var context: Context
                 ) :RepositoryInterface {

    companion object{
        private var instance:Repository? = null
        fun getInstance(remoteSource:RemoteSourceInterface,
                        context: Context):Repository{
            return instance?: Repository(remoteSource,context)
        }
    }

    override suspend fun getCurrentWeatherWithLocationInRepo(lat:Double,long:Double,unit:String): WeatherForecast {
        Log.i("TAG", "getCurrentWeatherWithLocationInRepoooooooooooooo: ")
        return remoteSource.getCurrentWeatherWithLocation(lat,long,unit)
    }
}