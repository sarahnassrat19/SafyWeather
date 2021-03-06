package com.example.safyweather.mapscreen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.safyweather.MY_SHARED_PREFERENCES
import com.example.safyweather.R
import com.example.safyweather.arrayOfUnits
import com.example.safyweather.db.LocalSource
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModel
import com.example.safyweather.favoritescreen.viewmodel.FavoriteViewModelFactory
import com.example.safyweather.model.Repository
import com.example.safyweather.model.RepositoryInterface
import com.example.safyweather.model.WeatherAddress
import com.example.safyweather.networking.RemoteSource

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var favViewModelFactory:FavoriteViewModelFactory
    private lateinit var favViewModel:FavoriteViewModel
    private val fragmentType:MapsFragmentArgs by navArgs()
    private var settings: com.example.safyweather.model.Settings? = null

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

            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage(getString(R.string.saveLocation))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.save)) { dialog, id ->
                    when(fragmentType.commingFragment) {
                        false -> {
                            val latIn4Digits: Double
                            val lonIn4Digits: Double
                            if(settings?.language as Boolean) {
                                latIn4Digits=
                                    String.format("%.4f", it.latitude).toDouble()
                                lonIn4Digits=
                                    String.format("%.4f", it.longitude).toDouble()
                            }
                            else{
                                latIn4Digits = it.latitude
                                lonIn4Digits = it.longitude
                            }
                            var selectedAddress = WeatherAddress(addressName, latIn4Digits, lonIn4Digits)
                            this.addWeatherWithAddress(selectedAddress)
                            //val action = MapsFragmentDirections.actionMapsFragmentToFavoriteFragment(it.latitude.toFloat(),it.longitude.toFloat(),addressName)
                            navController.navigateUp()
                        }
                        true -> {
                            settings?.location = 2
                            favViewModel.setSettingsSharedPrefs(settings as com.example.safyweather.model.Settings)
                            val action = MapsFragmentDirections.actionMapsFragmentToHomeFragment(it.latitude.toFloat(),it.longitude.toFloat(),
                                arrayOfUnits[settings?.unit as Int],true)
                            navController.navigate(action)
                        }
                        null -> {
                            Toast.makeText(requireContext(), "choose clear location!", Toast.LENGTH_SHORT).show()}
                    }
                    dialog.cancel()
                }
                .setNegativeButton(getString(R.string.cancel)) {dialog, id -> dialog.cancel()}
            val alert = dialogBuilder.create()
            alert.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        navController = Navigation.findNavController(activity as AppCompatActivity,
            R.id.nav_host_fragment
        )

        favViewModelFactory = FavoriteViewModelFactory(
            Repository.getInstance(
            RemoteSource.getInstance(),
            LocalSource.getInstance(requireActivity()),
            requireContext(),
            requireContext().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)))
        favViewModel = ViewModelProvider(this,favViewModelFactory).get(FavoriteViewModel::class.java)

        settings = favViewModel.getStoredSettings()

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