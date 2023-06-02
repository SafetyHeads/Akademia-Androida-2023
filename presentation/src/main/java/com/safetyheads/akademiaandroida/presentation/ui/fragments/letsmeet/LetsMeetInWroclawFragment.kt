package com.safetyheads.akademiaandroida.presentation.ui.fragments.letsmeet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentLetsMeetInWroclawBinding

class LetsMeetInWroclawFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentLetsMeetInWroclawBinding
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLetsMeetInWroclawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.apply {
            addMarker(
                MarkerOptions()
                    .position(SafetyHeadsObjects.safetyHeadsLocation)
                    .title(SafetyHeadsObjects.safetyHeadsName)
                    .snippet(SafetyHeadsObjects.safetyHeadsAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.splash_screen_logo))
                    .draggable(true)
            )?.showInfoWindow()
            moveCamera(CameraUpdateFactory.newLatLngZoom(SafetyHeadsObjects.safetyHeadsLocation, SafetyHeadsObjects.googleMapsDefaultZoom))
            setOnInfoWindowClickListener { openGoogleMaps() }
            setOnMapClickListener { openGoogleMaps() }
        }
    }

    private fun openGoogleMaps() {
        val mapIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(SafetyHeadsObjects.safetyHeadsGoogleMapUri)
            if (requireContext().packageManager.getLaunchIntentForPackage(SafetyHeadsObjects.googleMapsPackage) != null)
                setPackage("com.google.android.apps.maps")
        }
        startActivity(mapIntent)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}