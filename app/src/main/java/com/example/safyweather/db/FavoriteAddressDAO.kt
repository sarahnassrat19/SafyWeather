package com.example.safyweather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.safyweather.model.WeatherAddress

@Dao
interface FavoriteAddressDAO {
    @Query("SELECT * FROM addresses")
    fun getAllAddresses():LiveData<List<WeatherAddress>>

    @Insert
    fun insertFavoriteAddress(address:WeatherAddress)

    @Delete
    fun deleteFavoriteAddress(address: WeatherAddress)
}