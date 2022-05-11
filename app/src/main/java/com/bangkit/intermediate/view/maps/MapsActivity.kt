package com.bangkit.intermediate.view.maps

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.R
import com.bangkit.intermediate.databinding.ActivityMapsBinding
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.preference.ViewModelFactory
import com.bangkit.intermediate.view.home.StoriesAdapter
import com.bangkit.intermediate.viewmodel.HomeViewModel.HomeVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var homeVM : HomeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeVM = ViewModelProvider(
            this,
            ViewModelFactory(SessionPreference.getInstance(dataStore))
        )[HomeVM::class.java]

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        var token =""
        homeVM.getUser().observe(
            this
        ){
            token = it.token
            getStoriesResponse(token)
        }

        homeVM.list.observe(this){
            if (it!= null) {
                it.listStory?.forEach { data ->
                    if (data != null) {
                        if (data.lat != null || data.lon != null){
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(data.lat as Double, data.lon as Double ))
                                    .title("user : ${data.name}")
                            )
                        }
                    }
                }
            }
        }
    }

    fun getStoriesResponse(token:String){
        homeVM.getListStories(token)
    }
}