package com.example.safyweather.homescreen.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.safyweather.R
import com.example.safyweather.homescreen.viewmodel.HomeViewModel
import com.example.safyweather.homescreen.viewmodel.HomeViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.networking.RemoteSource

class HomeFragment : Fragment() {

    lateinit var city:TextView
    lateinit var dateAndTime:TextView
    lateinit var temp:TextView
    lateinit var desc:TextView
    lateinit var viewModelFactory:HomeViewModelFactory
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = HomeViewModelFactory(Repository.getInstance(RemoteSource.getInstance(),context as Context))
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        city = view.findViewById(R.id.currCity)
        dateAndTime = view.findViewById(R.id.currDate)
        temp = view.findViewById(R.id.currTemp)
        desc = view.findViewById(R.id.currDesc)

        viewModel.getWholeWeather()

        viewModel.weatherFromNetwork.observe(viewLifecycleOwner){
            Log.i("TAG", "onViewCreated: on observvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvve")
            if(it!=null){
                city.text = it.timezone
                dateAndTime.text = it.current.dt.toString()
                temp.text = it.current.temp.toString()
                desc.text = it.current.weather[0].description
            }
        }

    }

}