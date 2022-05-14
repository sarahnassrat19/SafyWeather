package com.example.safyweather.db

import android.content.Context
import androidx.room.*
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast
import com.example.safyweather.utilities.WeatherConverter

@Database(entities = arrayOf(WeatherForecast::class,WeatherAddress::class), version = 1)
@TypeConverters(WeatherConverter::class)
abstract class WeatherDatabase :RoomDatabase(){

    abstract fun weatherDao():WeatherDAO
    abstract fun addressesDao():FavoriteAddressDAO

    companion object{
        private var weatherDatabase:WeatherDatabase? = null

        fun getInstance(context: Context):WeatherDatabase{
            return weatherDatabase ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "WeatherDatabase").build()
                weatherDatabase = instance
                instance
            }
        }
    }

}