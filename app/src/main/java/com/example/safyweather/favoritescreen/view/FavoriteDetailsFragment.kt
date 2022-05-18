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
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.databinding.FragmentFavoriteDetailsBinding
import com.example.safyweather.db.LocalSource
import com.example.safyweather.homescreen.view.DailyWeatherAdapter
import com.example.safyweather.homescreen.view.HourlyWeatherAdapter
import com.example.safyweather.homescreen.viewmodel.HomeViewModel
import com.example.safyweather.homescreen.viewmodel.HomeViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.networking.RemoteSource
import com.example.safyweather.utilities.Converters

class FavoriteDetailsFragment : Fragment() {

    lateinit var viewModelFactory: HomeViewModelFactory
    lateinit var detailsViewModel: HomeViewModel
    lateinit var hourlyAdapter: HourlyWeatherAdapter
    lateinit var dailyAdapter: DailyWeatherAdapter
    lateinit var layoutManagerHourly: LinearLayoutManager
    lateinit var layoutManagerDaily: LinearLayoutManager
    lateinit var binding:FragmentFavoriteDetailsBinding
    private lateinit var navController: NavController

    val weatherToShow:FavoriteDetailsFragmentArgs by navArgs()
    var converter = Converters()

    /*val callback1 = requireActivity().onBackPressedDispatcher.addCallback(this) {

    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("TAG", "onCreateView: ")
        return inflater.inflate(R.layout.fragment_favorite_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("TAG", "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteDetailsBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)

        viewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                RemoteSource.getInstance(),
                LocalSource.getInstance(requireActivity()),
                requireContext(),
                requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)))
        detailsViewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        setupRecyclerViews()

        binding.currCity.text = weatherToShow.weather.timezone
        binding.currDate.text = Converters.getDateFormat(weatherToShow.weather.current.dt)
        binding.currTime.text = Converters.getTimeFormat(weatherToShow.weather.current.dt)
        binding.currTemp.text = weatherToShow.weather.current.temp.toString()
        binding.currDesc.text = weatherToShow.weather.current.weather[0].description
        Glide.with(context as Context)
            .load("https://openweathermap.org/img/wn/"+weatherToShow.weather.current.weather[0].icon+"@2x.png")
            .into(binding.currIcon)
        hourlyAdapter.setHourlyWeatherList(weatherToShow.weather.hourly)
        dailyAdapter.setDailyWeatherList(weatherToShow.weather.daily)

        hourlyAdapter.notifyDataSetChanged()
        dailyAdapter.notifyDataSetChanged()
        Log.i("TAG", "onViewCreated: finished")

    }

    fun setupRecyclerViews(){
        Log.i("TAG", "setupRecyclerViews: ")
        hourlyAdapter = HourlyWeatherAdapter(context as Context, arrayListOf())
        dailyAdapter = DailyWeatherAdapter(context as Context, arrayListOf())
        layoutManagerHourly = LinearLayoutManager(context as Context,LinearLayoutManager.HORIZONTAL,false)
        layoutManagerDaily = LinearLayoutManager(context as Context)
        binding.hourlyRecycler.adapter = hourlyAdapter
        binding.dailyRecycler.adapter = dailyAdapter
        binding.hourlyRecycler.layoutManager = layoutManagerHourly
        binding.dailyRecycler.layoutManager = layoutManagerDaily
    }

}