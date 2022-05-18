package com.example.safyweather.favoritescreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.R
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private var context: Context
    private var favAddresses:List<WeatherAddress>
    private var favWeatherList:List<WeatherForecast>
    private var onClickHandler:OnFavWeatherClickListener

    constructor(context: Context,
                favAddresses:List<WeatherAddress>,
                favWeatherList:List<WeatherForecast>,
                onClickHandler:OnFavWeatherClickListener){
        this.context = context
        this.favAddresses = favAddresses
        this.favWeatherList = favWeatherList
        this.onClickHandler = onClickHandler
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_layout,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.addressName.text = favAddresses[position].address
        holder.removeFav.setOnClickListener{ onClickHandler.onRemoveBtnClick(favAddresses[position],favWeatherList[position]) }
        holder.favConstraint.setOnClickListener { onClickHandler.onFavItemClick(favAddresses[position])}
    }

    override fun getItemCount(): Int {
        return favAddresses.size
    }

    fun setFavAddressesList(addressList:List<WeatherAddress>){
        this.favAddresses = addressList
    }

    fun setFavWeatherList(weatherList:List<WeatherForecast>){
        this.favWeatherList = weatherList
    }

    inner class FavoriteViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        val favConstraint:ConstraintLayout
        get() = view.findViewById(R.id.favConstraint)
        val addressName:TextView
        get() = view.findViewById(R.id.favAddressName)
        val removeFav:ImageView
        get() = view.findViewById(R.id.removeFav)
    }
}