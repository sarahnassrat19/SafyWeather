package com.example.safyweather.settingsscreen.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.databinding.FragmentSettingsBinding
import com.example.safyweather.db.LocalSource
import com.example.safyweather.location.InitialFragmentDirections
import com.example.safyweather.utilities.LocaleHelper
import com.example.safyweather.model.Repository
import com.example.safyweather.model.Settings
import com.example.safyweather.networking.RemoteSource
import com.example.safyweather.settingsscreen.viewmodel.SettingsViewModel
import com.example.safyweather.settingsscreen.viewmodel.SettingsViewModelFactory


class SettingsFragment : Fragment() {

    lateinit var settingsViewModel: SettingsViewModel
    lateinit var settingsViewModelFactory: SettingsViewModelFactory
    lateinit var binding:FragmentSettingsBinding
    private var settings: Settings? = null
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        settingsViewModelFactory = SettingsViewModelFactory(
            Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext(),
            requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)))
        settingsViewModel = ViewModelProvider(this,settingsViewModelFactory).get(SettingsViewModel::class.java)

        settings = settingsViewModel.getStoredSettings()

        setupUI()

        binding.notiEnable.setOnClickListener{
            settings?.notification = true
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.notiDisable.setOnClickListener{
            settings?.notification = false
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.GPS.setOnClickListener{
            settings?.location = 1
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.map.setOnClickListener{
            settings?.location = 2
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
            val action = SettingsFragmentDirections.actionSettingsFragmentToMapsFragment(true)
            navController.navigate(action)
        }

        binding.standardUnit.setOnClickListener{
            settings?.unit = 0
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.metricUnit.setOnClickListener{
            settings?.unit = 1
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.imperialUnit.setOnClickListener{
            settings?.unit = 2
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
        }

        binding.arabicLang.setOnClickListener{
            settings?.language = false
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
            LocaleHelper.setLocale(requireContext(),"ar")
        }

        binding.englishLang.setOnClickListener{
            settings?.language = true
            settingsViewModel.setSettingsSharedPrefs(settings as Settings)
            LocaleHelper.setLocale(requireContext(),"en")
        }

    }

    fun setupUI(){
        if(settings?.language as Boolean){ binding.englishLang.isChecked = true }
        else{ binding.arabicLang.isChecked = true }

        if(settings?.unit as Int == 0){binding.standardUnit.isChecked = true}
        else if(settings?.unit as Int == 1){binding.metricUnit.isChecked = true}
        else{binding.metricUnit.isChecked = true}

        if(settings?.location as Int == 1){binding.GPS.isChecked = true}
        else if(settings?.location as Int == 2){binding.map.isChecked = true}

        if(settings?.notification as Boolean){binding.notiEnable.isChecked = true}
        else{binding.notiDisable.isChecked = true}
    }

}