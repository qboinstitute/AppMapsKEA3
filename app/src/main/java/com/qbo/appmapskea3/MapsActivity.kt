package com.qbo.appmapskea3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMapClickListener
{

    private lateinit var mMap: GoogleMap
    var lstLatLng = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)
        mMap.setOnMapClickListener(this)
        // Add a marker in Sydney and move the camera
        val ubicacion = LatLng(-12.067852, -77.034596)
        mMap.addMarker(MarkerOptions()
            .position(ubicacion)
            .title("Mi marcador")
            .snippet("Punto de referencia.")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            .draggable(true))
        //mMap.isTrafficEnabled = true
        //Cambiar el tipo de MAPA.
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16.0F))

    }

    override fun onMarkerDragStart(p0: Marker?) {
        p0!!.hideInfoWindow()

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
        var posicion = p0!!.position
        p0!!.snippet = "Nueva ubicación: ${posicion.latitude.toString()}  ${posicion.longitude.toString()}"
        val nuevaubicacion = LatLng(posicion.latitude, posicion.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(nuevaubicacion))
        p0!!.showInfoWindow()
    }

    override fun onMapClick(p0: LatLng?) {
        mMap.addMarker(
            MarkerOptions()
                .position(p0!!)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .title("Nuevo marcador")
        ).showInfoWindow()
        lstLatLng.add(p0)
        val linea = PolylineOptions()
        linea.color(Color.RED)
        linea.width(5F)
        linea.addAll(lstLatLng)
        mMap.addPolyline(linea)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0!!))
    }
}