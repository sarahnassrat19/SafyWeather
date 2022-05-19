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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.databinding.FragmentFavoriteBinding
import com.example.safyweather.db.LocalSource
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModel
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModelFactory
import com.example.safyweather.location.InitialFragmentDirections
import com.example.safyweather.model.Repository
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.model.WeatherForecast
import com.example.safyweather.networking.RemoteSource
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteFragment : Fragment() ,OnFavWeatherClickListener{

    private lateinit var navController: NavController
    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var favViewModelFactory:FavoriteViewModelFactory
    private lateinit var favViewModel:FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)

        favViewModelFactory = FavoriteViewModelFactory(Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext(),
            requireContext().getSharedPreferences(MY_SHARED_PREFERENCES,MODE_PRIVATE)))
        favViewModel = ViewModelProvider(this,favViewModelFactory).get(FavoriteViewModel::class.java)

        setupFavRecycler()

        connectivity = context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if ( connectivity != null) {
            info = connectivity!!.activeNetworkInfo
            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    updateWeatherDatabase()
                } else{
                    binding.floatingAddFav.isEnabled = false
                }
            } else{
                binding.floatingAddFav.isEnabled = false
            }
        }

        val addressObserver = Observer<List<WeatherAddress>> {
            Log.i("TAG", "favoriteFragment on observvvvvvvvvvvvvvvvvve on get all addresses")
            if(it != null){
                favAdapter.setFavAddressesList(it)
            }
            favAdapter.notifyDataSetChanged()
        }
        favViewModel.getAllAddresses().observe(viewLifecycleOwner,addressObserver)

        val weatherObserver = Observer<List<WeatherForecast>> {
            Log.i("TAG", "fav fragment : in getAllWeathersInVvvvvvvvvvMmmmmmmmmmm")
            if(it != null) {
                favAdapter.setFavWeatherList(it)
            }
            favAdapter.notifyDataSetChanged()
        }
        favViewModel.getAllWeathersInVM().observe(viewLifecycleOwner,weatherObserver)

        //Toast.makeText(activity, "address: "+locationArgs.address, Toast.LENGTH_LONG).show()
        binding.floatingAddFav.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToMapsFragment(false)
            navController.navigate(action)
        }
    }

    fun setupFavRecycler(){
        Log.i("TAG", "setupFavRecycler: ")
        favAdapter = FavoriteAdapter(requireContext(), emptyList(),emptyList(),this)
        layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecycler.adapter = favAdapter
        binding.favoriteRecycler.layoutManager = layoutManager
    }

    /*fun getSelectedWeather(address: WeatherAddress){
        val latIn4Digits:Double = String.format("%.4f", address.lat).toDouble()
        val lonIn4Digits:Double = String.format("%.4f", address.lon).toDouble()
        favViewModel.getOneWeather(latIn4Digits,lonIn4Digits).observe(this){
            _selectedWeatherToRemove.postValue(it)
        }
    }*/

    override fun onRemoveBtnClick(address: WeatherAddress,weather:WeatherForecast) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(getString(R.string.deleteMsg))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.delete)) { dialog, id ->
                favViewModel.removeAddressFromFavorites(address)
                favViewModel.removeOneFavWeather(weather)
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, id -> dialog.cancel() }
        val alert = dialogBuilder.create()
        alert.show()
        //Log.i("TAG", "onRemoveBtnClick: adddddddddddddddrrrrrreeeeeessssssssssssss")
        //Log.i("TAG", "onRemoveBtnClick: wwwwwwwwwwwwwwweeeeeeeeeeeeettttthhhhhheeeeeeeeeerrrrrrrrrrr ${favAdapter.itemCount}")
    }

    override fun onFavItemClick(address: WeatherAddress) {
        Log.i("TAG", "onFavItemClick: ")
        //val latIn4Digits: Double = String.format("%.4f", address.lat).toDouble()
        //val lonIn4Digits: Double = String.format("%.4f", address.lon).toDouble()
        favViewModel.getOneWeather(address.lat,address.lon).observe(viewLifecycleOwner) {
            if(it == null){
                Log.i("TAG", "nnnnnnnnnnuuuuuuuuullllllllllllllllllllll222222222222222222222")
            }
            if(navController.currentDestination?.id == R.id.favoriteFragment) {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToFavoriteDetailsFragment(it)
                navController.navigate(action)
            }
        }
    }

    fun updateWeatherDatabase(){

        val observerName1 = Observer<List<WeatherAddress>> {
            for (favWeather in it){
                favViewModel.getFavWholeWeather(favWeather.lat,favWeather.lon,"metric")

                val observerName2 = Observer<WeatherForecast> { item ->
                    favViewModel.addOneFavWeather(item) }
                favViewModel.favWeatherFromNetwork.observe(viewLifecycleOwner,observerName2)
            }
        }
        favViewModel.getAllAddresses().observe(viewLifecycleOwner, observerName1)
        /*favViewModel.getAllAddresses().observe(viewLifecycleOwner){
            for (favWeather in it){
                favViewModel.getFavWholeWeather(favWeather.lat,favWeather.lon,"metric")
                favViewModel.favWeatherFromNetwork.observe(viewLifecycleOwner) {item ->
                    favViewModel.addOneFavWeather(item)
                }
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy: ")
    }

}