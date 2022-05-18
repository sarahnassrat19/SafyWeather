package com.example.safyweather.alertscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safyweather.model.RepositoryInterface

class AlertsViewModelFactory(private val repo: RepositoryInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlertsViewModel(repo) as T
    }
}