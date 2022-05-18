package com.example.safyweather.homescreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.safyweather.utilities.Converters
import com.example.safyweather.R
import com.example.safyweather.model.HourlyWeather

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>{

    private var context: Context
    private var hourlyWeather:List<HourlyWeather>
    var converter = Converters()

    constructor(context: Context,hourlyWeather:List<HourlyWeather>){
        this.context = context
        this.hourlyWeather = hourlyWeather
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item_layout,parent,false)
        return HourlyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HourlyWeatherViewHolder,
        position: Int
    ) {
        val oneHourlyWeather:HourlyWeather = hourlyWeather[position]
        holder.hourlyTime.text = Converters.getTimeFormat(oneHourlyWeather.dt)
        holder.hourlyTemp.text = oneHourlyWeather.temp.toString()
        holder.hourlyWindSpeed.text = oneHourlyWeather.wind_speed.toString()
        Glide.with(context)
            .load("https://openweathermap.org/img/wn/"+oneHourlyWeather.weather[0].icon+"@2x.png")
            .into(holder.hourlyIcon)
        //holder.hourlyIcon.setImageResource(R.drawable.hourly_icon)
    }

    override fun getItemCount(): Int {
        return hourlyWeather.size
    }

    fun setHourlyWeatherList(hourlyWeatherList:List<HourlyWeather>){
        this.hourlyWeather = hourlyWeatherList
    }

    inner class HourlyWeatherViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val hourlyTime:TextView
        get() = view.findViewById(R.id.hourlyTime)
        val hourlyTemp:TextView
        get() = view.findViewById(R.id.hourlyTemp)
        val hourlyWindSpeed:TextView
        get() = view.findViewById(R.id.hourlyWindSpeed)
        val hourlyIcon:ImageView
        get() = view.findViewById(R.id.hourlyIcon)
    }

}