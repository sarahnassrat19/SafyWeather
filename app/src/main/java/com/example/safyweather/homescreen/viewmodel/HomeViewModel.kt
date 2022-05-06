package com.example.safyweather.homescreen.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo:RepositoryInterface):ViewModel() {

    private val _weatherFromNetwork = MutableLiveData<WeatherForecast>()
    val weatherFromNetwork:LiveData<WeatherForecast> = _weatherFromNetwork

    fun getWholeWeather(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TAG", "getWholeWeather: in view model ")
            val response = repo.getCurrentWeatherWithLocationInRepo()

            _weatherFromNetwork.postValue(response)
        }
    }

}