package com.example.safyweather.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.safyweather.db.LocalSourceInterface
import com.example.safyweather.networking.RemoteSourceInterface

class Repository(var remoteSource: RemoteSourceInterface,
                 var localSource: LocalSourceInterface,
                 var context: Context
                 ) :RepositoryInterface {

    companion object{
        private var instance:Repository? = null
        fun getInstance(remoteSource:RemoteSourceInterface,
                        localSource:LocalSourceInterface,
                        context: Context):Repository{
            return instance?: Repository(remoteSource,localSource,context)
        }
    }

    override suspend fun getCurrentWeatherWithLocationInRepo(lat:Double,long:Double,unit:String): WeatherForecast {
        Log.i("TAG", "getCurrentWeatherWithLocationInRepoooooooooooooo: ")
        return remoteSource.getCurrentWeatherWithLocation(lat,long,unit)
    }

    override val storedAddresses: LiveData<List<WeatherAddress>>
        get() = localSource.getAllAddresses()
    //override val oneStoredWeather: LiveData<WeatherForecast>
        //get() =

    override fun getOneWeather(lat: Double, long: Double): LiveData<WeatherForecast> {
        return localSource.getWeatherWithLatLong(lat,long)
    }

    override fun insertFavoriteAddress(address: WeatherAddress) {
        localSource.insertFavoriteAddress(address)
    }

    override fun deleteFavoriteAddress(address: WeatherAddress) {
        localSource.deleteFavoriteAddress(address)
    }

    override fun insertWeather(weather: WeatherForecast) {
        localSource.insertWeather(weather)
    }

    override fun deleteWeather(weather: WeatherForecast) {
        localSource.deleteWeather(weather)
    }

}