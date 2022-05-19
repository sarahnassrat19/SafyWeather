package com.example.safyweather.homescreen.view

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.utilities.Converters
import com.example.safyweather.R
import com.example.safyweather.arrayOfUnits
import com.example.safyweather.databinding.FragmentHomeBinding
import com.example.safyweather.db.LocalSource
import com.example.safyweather.homescreen.viewmodel.HomeViewModel
import com.example.safyweather.homescreen.viewmodel.HomeViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.WeatherForecast
import com.example.safyweather.networking.RemoteSource

class HomeFragment : Fragment() {

    lateinit var animLoading: LottieAnimationView
    lateinit var viewModelFactory:HomeViewModelFactory
    lateinit var viewModel: HomeViewModel
    lateinit var hourlyAdapter:HourlyWeatherAdapter
    lateinit var dailyAdapter:DailyWeatherAdapter
    lateinit var layoutManagerHourly:LinearLayoutManager
    lateinit var layoutManagerDaily:LinearLayoutManager
    lateinit var binding: FragmentHomeBinding
    val locationArgs:HomeFragmentArgs by navArgs()
    private var settings: com.example.safyweather.model.Settings? = null
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                RemoteSource.getInstance(),
                LocalSource.getInstance(requireActivity()),
                requireContext(),
                requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)))
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        animLoading = view.findViewById(R.id.animationView)

        settings = viewModel.getStoredSettings()

        setupRecyclerViews()

        if(viewModel.getStoredCurrentWeather() == null) {
            Log.i("TAG", "neeeeeeeeeeeeeeeeeeeeeeeeewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
            //viewModel.getWholeWeather(27.1783 , 31.1859,"metric")
            viewModel.getWholeWeather(
                locationArgs.lat.toDouble(),
                locationArgs.long.toDouble(),
                locationArgs.unit
            )

            viewModel.weatherFromNetwork.observe(viewLifecycleOwner) {
                Log.i("TAG", "onViewCreated: on observvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvve")
                if (it != null) {
                    applyUIChange(it)
                    viewModel.addWeatherInVM(it)
                }
                hourlyAdapter.notifyDataSetChanged()
                dailyAdapter.notifyDataSetChanged()
            }

        }
        else{
            Log.i("TAG", "elssssssssssssssssssssseeeeeeeeeeeeeeeeeeeee ")
            connectivity = context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
            if ( connectivity != null) {
                info = connectivity!!.activeNetworkInfo
                Log.i("TAG", "connectivity != null")
                if (info != null) {
                    if (info!!.state == NetworkInfo.State.CONNECTED) {
                        Log.i("TAG", "uuuuuuuuuuuuuppppppppppppppdddddddddaaaaaaaattttttttteeeeeeeeeeee")
                        viewModel.updateWeatherPrefs(this)
                    }
                    /*else{
                        applyUIChange(viewModel.getStoredCurrentWeather())
                    }*/
                    Log.i("TAG", "info not nulllllllllll ")
                }
                else{
                    Log.i("TAG", "info ========== nulllllllllll ")
                }
            }
            applyUIChange(viewModel.getStoredCurrentWeather())

            hourlyAdapter.notifyDataSetChanged()
            dailyAdapter.notifyDataSetChanged()
        }

    }

    fun setupRecyclerViews(){
        hourlyAdapter = HourlyWeatherAdapter(context as Context, arrayListOf(),arrayOfUnits[settings?.unit as Int])
        dailyAdapter = DailyWeatherAdapter(context as Context, arrayListOf(),arrayOfUnits[settings?.unit as Int])
        layoutManagerHourly = LinearLayoutManager(context as Context,LinearLayoutManager.HORIZONTAL,false)
        layoutManagerDaily = LinearLayoutManager(context as Context)
        binding.hourlyRecycler.adapter = hourlyAdapter
        binding.dailyRecycler.adapter = dailyAdapter
        binding.hourlyRecycler.layoutManager = layoutManagerHourly
        binding.dailyRecycler.layoutManager = layoutManagerDaily
    }

    fun applyUIChange(currWeather:WeatherForecast?){
        currWeather as WeatherForecast
        animLoading.visibility = View.GONE
        binding.currCity.text = currWeather.timezone
        binding.currDate.text = Converters.getDateFormat(currWeather.current.dt)
        binding.currTime.text = Converters.getTimeFormat(currWeather.current.dt)
        binding.currTemp.text = currWeather.current.temp.toString()
        binding.currDesc.text = currWeather.current.weather[0].description
        Log.i("TAG", "applyUIChange:---------------------------------------- ${currWeather.current.weather[0].description}")
        binding.currHumidity.text = currWeather.current.humidity.toString()
        binding.currWindSpeed.text = currWeather.current.wind_speed.toString()
        binding.currClouds.text = currWeather.current.clouds.toString()
        binding.currPressure.text = currWeather.current.pressure.toString()
        when(arrayOfUnits[settings?.unit as Int]) {
            "standard" ->{
                binding.currUnit.text = getString(R.string.Kelvin)
                binding.currWindUnit.text = getString(R.string.windMeter)
            }
            "metric" ->{
                binding.currUnit.text = getString(R.string.Celsius)
                binding.currWindUnit.text = getString(R.string.windMeter)
            }
            "imperial" ->{
                binding.currUnit.text = getString(R.string.Fahrenheit)
                binding.currWindUnit.text = getString(R.string.windMile)
            }
        }
        Glide.with(context as Context)
            .load("https://openweathermap.org/img/wn/"+currWeather.current.weather[0].icon+"@2x.png")
            .into(binding.currIcon)
        hourlyAdapter.setHourlyWeatherList(currWeather.hourly)
        dailyAdapter.setDailyWeatherList(currWeather.daily)
    }
}