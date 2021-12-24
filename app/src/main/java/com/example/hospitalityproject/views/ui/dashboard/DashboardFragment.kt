package com.example.hospitalityproject.views.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.Hospital
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.*

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map:GoogleMap;
    private lateinit var db: FirebaseFirestore
    private lateinit var hospitalsArrayList: ArrayList<Hospital>
    private lateinit var hospitalsIds: ArrayList<String>
    private lateinit var markersLatLng : ArrayList<LatLng>
    private lateinit var markersTitle : ArrayList<String>

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hospitalsArrayList = arrayListOf()
        hospitalsIds = arrayListOf()
        markersLatLng = ArrayList()
        markersTitle = ArrayList()

        db = FirebaseFirestore.getInstance()
        db.collection("hospitals").whereEqualTo("status", true).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    hospitalsArrayList.add(document.toObject(Hospital::class.java));
                    hospitalsIds.add(document.reference.id)
                    Log.d("Test789aaaaaaaaaaa", "$hospitalsArrayList ${document.reference.id} " )
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)

        Handler().postDelayed(
            {
                createFragment()
            },
            2000
        )
        Log.d("Test7891cccccccccccccccc", hospitalsArrayList.toString())
        return view
    }



    private fun createFragment() {
        val fragmentManager: FragmentManager = childFragmentManager;
        val mapFragment: SupportMapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Handler().postDelayed(
            {
                createMarkers()
                enableLocation()
            },
            2000
        )
    }

    private fun createMarkers(){
        Log.d("Test789ddddddddddd", markersLatLng.toString())
        for(i in 0 until hospitalsArrayList.size){
            markersLatLng.add(LatLng(hospitalsArrayList.get(i).ubication!!.latitude, hospitalsArrayList.get(i).ubication!!.longitude))
            markersTitle.add(hospitalsArrayList.get(i).name.toString())
        }
        for(i in 0 until markersLatLng.size){
            val marker = map.addMarker(
                MarkerOptions()
                .position(markersLatLng[i])
                .title(markersTitle[i])
            )
            marker?.tag = 0
        }
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(-16.392076, -71.536833), 12f),
            4000,
            null
        )
    }

    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        activity!!,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isPermissionsGranted()){
            map.isMyLocationEnabled = true
        }
        else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(activity!!,"Debe aceptar los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }
            else{
                Toast.makeText(activity!!,"Debe aceptar los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}