package com.example.safyweather.homescreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.R

class DailyWeatherAdapter:RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder> {

    private var context: Context
    //private var dailyWeather:List<DailyWeather>

    constructor(context: Context/*,dailyWeather:List<DailyWeather>*/){
        this.context = context
        //this.dailyWeather = dailyWeather
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_item_layout,parent,false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DailyWeatherViewHolder,
        position: Int
    ) {
        /*val oneDailyWeather:DailyWeather = dailyWeather[position]
        holder.dailyDate.text = oneDailyWeather.date
        holder.dailyDesc.text = oneDailyWeather.desc
        holder.dailyTemp.text = oneDailyWeather.temp
        holder.dailyIcon.setImageResource(R.drawable.daily_icon)*/
    }

    override fun getItemCount(): Int {
        //return dailyWeather.size
        return 0
    }

    inner class DailyWeatherViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val dailyDate:TextView
            get() =view.findViewById(R.id.dailyDate)
        val dailyDesc:TextView
            get() =view.findViewById(R.id.dailyDesc)
        val dailyTemp:TextView
            get() =view.findViewById(R.id.dailyTemp)
        val dailyIcon:ImageView
            get() = view.findViewById(R.id.dailyIcon)
    }
}