package com.example.safyweather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.navigateUp
import com.example.safyweather.db.LocalSource
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModel
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModelFactory
import com.example.safyweather.location.InitialFragmentDirections
import com.example.safyweather.model.Repository
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.networking.RemoteSource

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {

    lateinit var navController: NavController
    private lateinit var favViewModelFactory:FavoriteViewModelFactory
    private lateinit var favViewModel:FavoriteViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val assiut = LatLng(27.174926, 31.192810)
        googleMap.addMarker(MarkerOptions().position(assiut).title("Assiut"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(assiut,8f))

        googleMap.setOnMapClickListener{
            var addressName = getAddressFromLatLng(it.latitude,it.longitude)
            googleMap.clear()

            val someLocation = LatLng(it.latitude, it.longitude)
            googleMap.addMarker(MarkerOptions().position(someLocation).title(addressName))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(someLocation,8f))

            val latIn4Digits:Double = String.format("%.4f", it.latitude).toDouble()
            val lonIn4Digits:Double = String.format("%.4f", it.longitude).toDouble()
            var selectedAddress = WeatherAddress(addressName,latIn4Digits,lonIn4Digits)
            this.addWeatherWithAddress(selectedAddress)
            //val action = MapsFragmentDirections.actionMapsFragmentToFavoriteFragment( it.latitude.toFloat(),it.longitude.toFloat(),addressName)
            navController.navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        navController = Navigation.findNavController(activity as AppCompatActivity,R.id.nav_host_fragment)

        favViewModelFactory = FavoriteViewModelFactory(
            Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext()))
        favViewModel = ViewModelProvider(this,favViewModelFactory).get(FavoriteViewModel::class.java)

        mapFragment?.getMapAsync(callback)

    }

    fun getAddressFromLatLng(lat:Double,longg:Double) : String{
        var geocoder = Geocoder(context as Context, Locale.getDefault())
        var addresses:List<Address>

        addresses = geocoder.getFromLocation(lat,longg,1)
        if(addresses.size>0) {
            return addresses.get(0).getAddressLine(0)
        }
        return ""
    }

    fun addWeatherWithAddress(address:WeatherAddress){
        favViewModel.addAddressToFavorites(address)
        /*favViewModel.getFavWholeWeather(address.lat,address.lon,"Standard")
        favViewModel.favWeatherFromNetwork.observe(this){
            Log.i("TAG", "addWeatherWithAddress: observvvvvvvvvvvvvvvvvvve")
            favViewModel.addOneFavWeather(it)
        }*/
        Log.i("TAG", "addWeatherWithAddress: ")
    }
}