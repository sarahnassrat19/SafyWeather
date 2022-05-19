package com.example.safyweather.splashscreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.safyweather.*
import com.example.safyweather.databinding.FragmentSplashBinding
import com.example.safyweather.db.LocalSource
import com.example.safyweather.utilities.LocaleHelper
import com.example.safyweather.model.Repository
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.Settings
import com.example.safyweather.model.WeatherForecast
import com.example.safyweather.networking.RemoteSource

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    private var currentWeather:WeatherForecast? = null
    private var settings:Settings? = null
    private lateinit var repo:RepositoryInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)

        Handler().postDelayed({
            if(currentWeather == null){
                navController.navigate(R.id.action_splashFragment_to_initialFragment)
            }
            else{
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment(
                    currentWeather?.lat?.toFloat() as Float,
                    currentWeather?.lon?.toFloat() as Float,
                    arrayOfUnits[settings?.unit as Int])
                navController.navigate(action)
            }
        }, 1000)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext(),
            requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE))
        currentWeather = repo.getWeatherSharedPreferences()
        settings = repo.getSettingsSharedPreferences()
        if(settings == null){
            this.settings = Settings(ENGLISH,STANDARD,NONE,ENABLED)
            repo.addSettingsToSharedPreferences(settings as Settings)
        }
        else{
            if(settings?.language as Boolean) {
                LocaleHelper.setLocale(requireContext(), "en")
            }
            else{
                LocaleHelper.setLocale(requireContext(), "ar")
            }
        }
    }

}