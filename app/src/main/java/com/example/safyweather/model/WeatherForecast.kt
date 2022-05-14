package com.example.safyweather.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.TypeConverter
import com.example.safyweather.utilities.WeatherConverter
import java.io.Serializable
import java.sql.Timestamp

@Entity(primaryKeys = arrayOf("lat", "lon"), tableName = "weathers")
data class WeatherForecast(@NonNull
                           var lat:Double,
                           @NonNull
                           var lon:Double,
                           var timezone:String,
                           var current:CurrentWeather,
                           var hourly:List<HourlyWeather>,
                           var daily:List<DailyWeather>): Serializable

data class CurrentWeather(var dt:Int,
                          var temp:Double,
                          var pressure:Int,
                          var humidity:Int,
                          var clouds:Int,
                          var wind_speed:Double,
                          var weather:List<Weather>)

data class HourlyWeather(var dt:Int,
                         var temp:Double,
                         var wind_speed:Double,
                         var weather:List<Weather>)

data class DailyWeather(var dt:Int,
                        var temp:Temprature,
                        var weather:List<Weather>)

data class Weather(var id:Int,
                   var main:String,
                   var description:String,
                   var icon:String)

data class Temprature(var day:Double,
                      var min:Double,
                      var max:Double,
                      var night:Double,
                      var eve:Double,
                      var morn:Double)

@Entity(primaryKeys = arrayOf("lat", "lon"), tableName = "addresses")
data class WeatherAddress(var address:String,
                          @NonNull
                          var lat:Double,
                          @NonNull
                          var lon:Double)