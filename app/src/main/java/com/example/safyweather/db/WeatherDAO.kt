package com.example.safyweather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.safyweather.model.WeatherForecast

@Dao
interface WeatherDAO {

    @Insert(onConflict = REPLACE)
    fun insertWeather(weather:WeatherForecast)

    @Delete
    fun deleteWeather(weather: WeatherForecast)

    @Query("SELECT * FROM weathers WHERE lat == :latt AND lon == :longg")
    fun getWeatherWithLatLong(latt:Double,longg:Double): LiveData<WeatherForecast>
}
