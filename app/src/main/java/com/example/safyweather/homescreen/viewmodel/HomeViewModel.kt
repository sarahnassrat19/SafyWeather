package com.example.safyweather.homescreen.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.Settings
import com.example.safyweather.model.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo:RepositoryInterface):ViewModel() {

    private val _weatherFromNetwork = MutableLiveData<WeatherForecast>()
    val weatherFromNetwork:LiveData<WeatherForecast> = _weatherFromNetwork

    fun getWholeWeather(lat:Double,long:Double,unit:String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TAG", "getWholeWeather: in view model ")
            val response = repo.getCurrentWeatherWithLocationInRepo(lat,long,unit)
            _weatherFromNetwork.postValue(response)
        }
    }

    fun getStoredSettings(): Settings?{
        return repo.getSettingsSharedPreferences()
    }

    fun getStoredCurrentWeather(): WeatherForecast?{
        return repo.getWeatherSharedPreferences()
    }

    fun addWeatherInVM(weather: WeatherForecast){
        repo.addWeatherToSharedPreferences(weather)
    }

    fun updateWeatherPrefs(owner: LifecycleOwner){
        Log.i("TAG", "upppppppppppppddddddddaaaaaaaaatttttttee on view model")
        getWholeWeather(repo.getWeatherSharedPreferences()?.lat as Double,repo.getWeatherSharedPreferences()?.lon as Double,"metric")
        weatherFromNetwork.observe(owner){
            repo.addWeatherToSharedPreferences(it)
        }
    }

}