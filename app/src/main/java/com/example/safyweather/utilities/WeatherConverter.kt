package com.example.safyweather.utilities

import androidx.room.TypeConverter
import com.example.safyweather.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverter {
    var gson = Gson()

    //current weather
    @TypeConverter
    fun currentWeatherToString(currentWeather: CurrentWeather):String{
        return gson.toJson(currentWeather)
    }
    @TypeConverter
    fun stringToCurrentWeather(currentWeatherString: String):CurrentWeather{
        return gson.fromJson(currentWeatherString,CurrentWeather::class.java)
    }

    //weather
    @TypeConverter
    fun weatherToString(weather: Weather):String{
        return gson.toJson(weather)
    }
    @TypeConverter
    fun stringToWeather(weatherString:String):Weather{
        return gson.fromJson(weatherString,Weather::class.java)
    }

    //weather list
    @TypeConverter
    fun weatherListToString(weatherList: List<Weather>):String{
        return gson.toJson(weatherList)
    }
    @TypeConverter
    fun stringToWeatherList(weatherListString:String):List<Weather>{
        var list = object :TypeToken<List<Weather>>(){}.type
        return gson.fromJson(weatherListString, list)
    }

    //hourly
    @TypeConverter
    fun hourlyWeatherToString(hourlyWeather:HourlyWeather):String{
        return gson.toJson(hourlyWeather)
    }
    @TypeConverter
    fun stringToHourlyWeather(hourlyWeatherString:String):HourlyWeather{
        return gson.fromJson(hourlyWeatherString,HourlyWeather::class.java)
    }

    //hourly list
    @TypeConverter
    fun hourlyListToString(hourlyList:List<HourlyWeather>):String{
        return gson.toJson(hourlyList)
    }
    @TypeConverter
    fun stringToHourlyList(hourlyListString:String):List<HourlyWeather>{
        var list = object :TypeToken<List<HourlyWeather>>(){}.type
        return gson.fromJson(hourlyListString,list)
    }

    //daily
    @TypeConverter
    fun dailyWeatherToString(dailyWeather:DailyWeather):String{
        return gson.toJson(dailyWeather)
    }
    @TypeConverter
    fun stringToDailyWeather(dailyWeatherString:String):DailyWeather{
        return gson.fromJson(dailyWeatherString,DailyWeather::class.java)
    }

    //daily list
    @TypeConverter
    fun dailyListToString(dailyList:List<DailyWeather>):String{
        return gson.toJson(dailyList)
    }
    @TypeConverter
    fun stringToDailyList(dailyListString:String):List<DailyWeather>{
        var list = object :TypeToken<List<DailyWeather>>(){}.type
        return gson.fromJson(dailyListString,list)
    }

    //temprature
    @TypeConverter
    fun tempratureToString(temprature: Temprature):String{
        return gson.toJson(temprature)
    }
    @TypeConverter
    fun stringToTemprature(tempratureString:String):Temprature{
        return gson.fromJson(tempratureString,Temprature::class.java)
    }

}