package com.group4.edusurveyor.module.map.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.credentials.PublicKeyCredential

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.group4.edusurveyor.R
import com.group4.edusurveyor.databinding.ActivityMapsBinding
import com.group4.edusurveyor.module.auth.view.LoginActivity
import com.group4.edusurveyor.module.detail.view.DetailActivity
import com.group4.edusurveyor.module.main.view.MainActivity

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val jakartaPos = LatLng(-6.121435, 106.774124)
        mMap.addMarker(MarkerOptions().position(jakartaPos).title("Jakarta"))?.showInfoWindow()

        val tangerangPos = LatLng( -6.17139, 106.64056)
        mMap.addMarker(MarkerOptions().position(tangerangPos).title("Tangerang"))?.showInfoWindow()

        val depokPos = LatLng( -6.405031, 106.8173077)
        mMap.addMarker(MarkerOptions().position(depokPos).title("Depok"))?.showInfoWindow()

        mMap.moveCamera(CameraUpdateFactory.newLatLng(jakartaPos))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11F))

        mMap.setOnMarkerClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            true
        }
    }
}