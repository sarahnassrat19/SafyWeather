package com.example.safyweather.homescreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.R

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>{

    private var context: Context
    //private var hourlyWeather:List<HourlyWeather>

    constructor(context: Context/*,hourlyWeather:List<HourlyWeather>*/){
        this.context = context
        //this.hourlyWeather = hourlyWeather
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
        /*val oneHourlyWeather:HourlyWeather = hourlyWeather[position]
        holder.hourlyTime.text = oneHourlyWeather.time
        holder.hourlyTemp.text = oneHourlyWeather.temp
        holder.hourlyWindSpeed.text = oneHourlyWeather.windSpeed
        holder.hourlyIcon.setImageResource(R.drawable.hourly_icon)*/
    }

    override fun getItemCount(): Int {
        //return hourlyWeather.size
        return 0
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