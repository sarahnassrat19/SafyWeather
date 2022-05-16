package com.example.safyweather.favoritescreen.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.db.LocalSource
import com.example.safyweather.homescreen.view.DailyWeatherAdapter
import com.example.safyweather.homescreen.view.HourlyWeatherAdapter
import com.example.safyweather.homescreen.viewmodel.HomeViewModel
import com.example.safyweather.homescreen.viewmodel.HomeViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.networking.RemoteSource
import com.example.safyweather.utilities.Converters

class FavoriteDetailsFragment : Fragment() {

    lateinit var city: TextView
    lateinit var currDate: TextView
    lateinit var currTime: TextView
    lateinit var temp: TextView
    lateinit var desc: TextView
    lateinit var icon: ImageView
    //lateinit var animLoading: LottieAnimationView

    lateinit var viewModelFactory: HomeViewModelFactory
    lateinit var detailsViewModel: HomeViewModel

    lateinit var hourlyRecycler: RecyclerView
    lateinit var dailyRecycler: RecyclerView
    lateinit var hourlyAdapter: HourlyWeatherAdapter
    lateinit var dailyAdapter: DailyWeatherAdapter
    lateinit var layoutManagerHourly: LinearLayoutManager
    lateinit var layoutManagerDaily: LinearLayoutManager

    val weatherToShow:FavoriteDetailsFragmentArgs by navArgs()

    var converter = Converters()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                RemoteSource.getInstance(),
                LocalSource.getInstance(requireActivity()),
                requireContext(),
                requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)))
        detailsViewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        city = view.findViewById(R.id.currCity)
        currDate = view.findViewById(R.id.currDate)
        currTime = view.findViewById(R.id.currTime)
        temp = view.findViewById(R.id.currTemp)
        desc = view.findViewById(R.id.currDesc)
        icon = view.findViewById(R.id.currIcon)
        hourlyRecycler = view.findViewById(R.id.hourlyRecycler)
        dailyRecycler = view.findViewById(R.id.dailyRecycler)
        //animLoading = view.findViewById(R.id.animationView)

        hourlyAdapter = HourlyWeatherAdapter(context as Context, arrayListOf())
        dailyAdapter = DailyWeatherAdapter(context as Context, arrayListOf())
        layoutManagerHourly = LinearLayoutManager(context as Context,LinearLayoutManager.HORIZONTAL,false)
        layoutManagerDaily = LinearLayoutManager(context as Context)
        hourlyRecycler.adapter = hourlyAdapter
        dailyRecycler.adapter = dailyAdapter
        hourlyRecycler.layoutManager = layoutManagerHourly
        dailyRecycler.layoutManager = layoutManagerDaily

        city.text = weatherToShow.weather.timezone
        currDate.text = converter.getDateFormat(weatherToShow.weather.current.dt)
        currTime.text = converter.getTimeFormat(weatherToShow.weather.current.dt)
        temp.text = weatherToShow.weather.current.temp.toString()
        desc.text = weatherToShow.weather.current.weather[0].description
        Glide.with(context as Context)
            .load("https://openweathermap.org/img/wn/"+weatherToShow.weather.current.weather[0].icon+"@2x.png")
            .into(icon)
        hourlyAdapter.setHourlyWeatherList(weatherToShow.weather.hourly)
        dailyAdapter.setDailyWeatherList(weatherToShow.weather.daily)

        hourlyAdapter.notifyDataSetChanged()
        dailyAdapter.notifyDataSetChanged()

        //detailsViewModel.getWholeWeather( weatherToShow.weather.lat, weatherToShow.weather.lon, "metric")
        //detailsViewModel.weatherFromNetwork.observe(viewLifecycleOwner){
            //Log.i("TAG", "onViewCreated: on observvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvve")
            //if(it!=null){
                //animLoading.visibility = View.GONE

            //}

        //}

    }

}