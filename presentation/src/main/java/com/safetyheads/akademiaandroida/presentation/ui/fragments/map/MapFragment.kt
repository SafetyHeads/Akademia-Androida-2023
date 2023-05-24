package com.safetyheads.akademiaandroida.presentation.ui.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }
    private fun initUI() {
        var mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
        }
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(map: GoogleMap) {
        val warsaw = LatLng(52.0, 21.0)
        map.addMarker(
                MarkerOptions()
                        .position(warsaw)
                        .title("Marker in Warsaw"))
        map.moveCamera(CameraUpdateFactory.newLatLng(warsaw))
    }
}
