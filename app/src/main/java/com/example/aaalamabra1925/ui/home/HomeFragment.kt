package com.example.aaalamabra1925.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.app.Service
import android.app.Service.*
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.Bundle
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.aaalamabra1925.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.compass.CompassOverlay
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.*
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.util.jar.Manifest


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private var mCompassOverlay: CompassOverlay? = null
    private var mScaleBarOverlay: ScaleBarOverlay? = null
    private var mRotationGestureOverlay: RotationGestureOverlay? = null
    private var btCenterMap: ImageButton? = null
    private var btFollowMe: ImageButton? = null
    private var lm: LocationManager? = null
    private lateinit var mLocationListener: GpsMyLocationProvider



    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10



    abstract class mylocationlistener : LocationListener {

        override fun onLocationChanged(location:Location) {
            val latitude=location.getLatitude();
            val longitude=location.getLongitude();
            val msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            //Toast.makeText(getBaseContext() ,msg,Toast.LENGTH_LONG).show();
        }

    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener()
    {
        lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        val c = Criteria()
        c.accuracy = Criteria.ACCURACY_FINE;

        val PROVIDER = lm?.getBestProvider(c, true);

        this.mLocationListener = GpsMyLocationProvider(this.context)
        this.lm!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0.0F, this.mLocationListener);
        //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0.0F, myLocationListener);
        Log.d("LOC_SERVICE", "Service RUNNING!");
    }

    @SuppressLint("MissingPermission", "LongLogTag")
    fun getLastKnownLocation(): Location? {
        val providers = lm!!.getProviders(true)
        var bestLocation: Location? = null

        for (provider in providers) run {
            val l: Location = lm!!.getLastKnownLocation(provider)


            if (bestLocation == null
                    || l.getAccuracy() < bestLocation!!.accuracy) {

                bestLocation = l
            }
        }
  if (bestLocation == null) {
    return null
  }
  return bestLocation
}



    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined

        mLastLocation = location
        if (mLastLocation != null) {
            // Update the UI from here
        }
    }


    fun startLocationUpdates() {

         // Create the location request to start receiving updates
         val mLocationRequest = LocationRequest()
         mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
         mLocationRequest!!.interval = INTERVAL
         mLocationRequest!!.fastestInterval = FASTEST_INTERVAL

         // Create LocationSettingsRequest object using location request
         val builder = LocationSettingsRequest.Builder()
         builder.addLocationRequest(mLocationRequest!!)
         val locationSettingsRequest = builder.build()

         val settingsClient = LocationServices.getSettingsClient(this.activity!!)
         settingsClient.checkLocationSettings(locationSettingsRequest)

          mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
          // new Google API SDK v11 uses getFusedLocationProviderClient(this)
          /*if (ActivityCompat.checkSelfPermission(this.activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


              return
          }*/
          mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                                                Looper.myLooper())
        }

        fun stoplocationUpdates() {
            mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
        }

        fun buildAlertMessageNoGps() {

            val builder = AlertDialog.Builder(this.activity!!)
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                , 11)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.cancel()

                    }
            val alert: AlertDialog = builder.create()
            Toast.makeText(this.activity,  "NO GPS" , Toast.LENGTH_LONG).show()
            //alert.show()


        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val mapView = root.findViewById<MapView>(R.id.openmapview)
        Configuration.getInstance().userAgentValue = context!!.packageName
        val dm = this.context!!.resources.displayMetrics

        mCompassOverlay = CompassOverlay(
            context, InternalCompassOrientationProvider(context),
            mapView
        )
        mLocationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(context),
            mapView
        )

        val myMapController = mapView.controller
        myMapController.setZoom(18.0)
        //myMapController.setCenter(GeoPoint(37.1970, -3.624))

        mScaleBarOverlay = ScaleBarOverlay(mapView)
        mScaleBarOverlay!!.setCentred(true)
        mScaleBarOverlay!!.setScaleBarOffset(dm.widthPixels / 2, 10)

        mRotationGestureOverlay = RotationGestureOverlay(mapView)
        mRotationGestureOverlay!!.isEnabled = true

        mapView.isTilesScaledToDpi = true
        mapView.setMultiTouchControls(true)
        mapView.isFlingEnabled = true
        mapView.overlays.add(mLocationOverlay)
        mapView.overlays.add(mCompassOverlay)
        mapView.overlays.add(mScaleBarOverlay)

        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        mLocationOverlay!!.isOptionsMenuEnabled = true
        mCompassOverlay!!.enableCompass()

        val test = Marker(mapView)
        test.position = GeoPoint(37.197152, -3.624137)
        test.textLabelFontSize = 40
        //test.setTextIcon("Etiqueta de prueba")
        test.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
        //test.icon = R.drawable.current_position_tennis_ball
        test.infoWindow = null
        mapView.overlays.add(test)
        
        test.setOnMarkerClickListener { marker, mapView ->
            Toast.makeText(this.activity,  "Marker's Listener invoked" , Toast.LENGTH_LONG).show()
            true
        }

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mLocationRequest = LocationRequest()

        lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if (!lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }

        addLocationListener()
        //val location = getLastKnownLocation()

        //val senialgps = location!!.accuracy
        //Toast.makeText(this.activity,  senialgps.toString() , Toast.LENGTH_LONG).show()

        //ESTO NO FUNCIONA
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.isCostAllowed = false

        val providerName = lm!!.getBestProvider(criteria, true)
        if(providerName != null){
            Toast.makeText(this.activity,  "NO GPS provider" , Toast.LENGTH_LONG).show()
        }
        //ESTO NO FUNCIONA


        return root
    }


}

