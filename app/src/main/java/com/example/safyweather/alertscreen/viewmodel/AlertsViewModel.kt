package com.example.safyweather.alertscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safyweather.model.AlertData
import com.example.safyweather.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertsViewModel(private val repo: RepositoryInterface): ViewModel() {

    fun getAllAlertsInVM():LiveData<List<AlertData>>{
        return repo.getAllAlertsInRepo()
    }

    fun addAlertInVM(alert:AlertData){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertAlertInRepo(alert)
        }
    }

    fun removeAlertInVM(alert:AlertData){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAlertInRepo(alert)
        }
    }

}