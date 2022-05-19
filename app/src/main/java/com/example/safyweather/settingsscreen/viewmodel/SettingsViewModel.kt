package com.example.safyweather.settingsscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.Settings

class SettingsViewModel (private val repo: RepositoryInterface):ViewModel(){
    fun setSettingsSharedPrefs(settings: Settings){
        repo.addSettingsToSharedPreferences(settings)
    }
    fun getStoredSettings(): Settings?{
        return repo.getSettingsSharedPreferences()
    }
}