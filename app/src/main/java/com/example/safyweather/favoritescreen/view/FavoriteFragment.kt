package com.example.safyweather.favoritescreen.view

import android.app.Service
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.db.LocalSource
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModel
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast
import com.example.safyweather.networking.RemoteSource
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteFragment : Fragment() ,OnFavWeatherClickListener{

    private lateinit var btnAddToFav:FloatingActionButton
    private lateinit var navController: NavController
    //private val locationArgs: FavoriteFragmentArgs by navArgs()
    private lateinit var favRecycler:RecyclerView
    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var favViewModelFactory:FavoriteViewModelFactory
    private lateinit var favViewModel:FavoriteViewModel
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null

    private var _selectedWeatherToRemove = MutableLiveData<WeatherForecast>()
    var selectedWeatherToRemove : LiveData<WeatherForecast> = _selectedWeatherToRemove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favViewModelFactory = FavoriteViewModelFactory(Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext(),
            requireContext().getSharedPreferences(MY_SHARED_PREFERENCES,MODE_PRIVATE)))

        favViewModel = ViewModelProvider(this,favViewModelFactory).get(FavoriteViewModel::class.java)

        navController = Navigation.findNavController(activity as AppCompatActivity,R.id.nav_host_fragment)
        btnAddToFav = view.findViewById(R.id.floatingAddFav)
        favRecycler = view.findViewById(R.id.favoriteRecycler)
        favAdapter = FavoriteAdapter(requireContext(), emptyList(),emptyList(),this)
        layoutManager = LinearLayoutManager(requireContext())
        favRecycler.adapter = favAdapter
        favRecycler.layoutManager = layoutManager


        favViewModel.updateWeatherDatabase(this)

        /*connectivity = context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if ( connectivity != null)
        {
            info = connectivity!!.activeNetworkInfo
            if (info != null)
            {
                if (info!!.state == NetworkInfo.State.CONNECTED)
                {

                }
            }
        }*/

        favViewModel.getAllAddresses().observe(this){
            Log.i("TAG", "favoriteFragment on observvvvvvvvvvvvvvvvvve on get all addresses")
            if(it != null){
                favAdapter.setFavAddressesList(it)
            }
            favAdapter.notifyDataSetChanged()
        }

        favViewModel.getAllWeathersInVM().observe(this){
            Log.i("TAG", "fav fragment : in getAllWeathersInVvvvvvvvvvMmmmmmmmmmm")
            if(it != null) {
                favAdapter.setFavWeatherList(it)
            }
            favAdapter.notifyDataSetChanged()
        }

        //Toast.makeText(activity, "address: "+locationArgs.address, Toast.LENGTH_LONG).show()
        btnAddToFav.setOnClickListener {
            navController.navigate(R.id.action_favoriteFragment_to_mapsFragment)
        }
    }

    fun getSelectedWeather(address: WeatherAddress){
        val latIn4Digits:Double = String.format("%.4f", address.lat).toDouble()
        val lonIn4Digits:Double = String.format("%.4f", address.lon).toDouble()
        favViewModel.getOneWeather(latIn4Digits,lonIn4Digits).observe(this){
            _selectedWeatherToRemove.postValue(it)
        }
    }

    override fun onRemoveBtnClick(address: WeatherAddress,weather:WeatherForecast) {
        Log.i("TAG", "onRemoveBtnClick: adddddddddddddddrrrrrreeeeeessssssssssssss")
        favViewModel.removeAddressFromFavorites(address)
        Log.i("TAG", "onRemoveBtnClick: wwwwwwwwwwwwwwweeeeeeeeeeeeettttthhhhhheeeeeeeeeerrrrrrrrrrr ${favAdapter.itemCount}")
        favViewModel.removeOneFavWeather(weather)

    }

    override fun onFavItemClick(address: WeatherAddress) {
        favViewModel.getOneWeather(address.lat,address.lon).observe(this) {
            if(it == null){
                Log.i("TAG", "nnnnnnnnnnuuuuuuuuullllllllllllllllllllll222222222222222222222")
            }
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToFavoriteDetailsFragment(it)
            navController.navigate(action)
        }
    }

}