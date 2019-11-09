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
import android.os.*
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.util.jar.Manifest


class HomeFragment() : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private var mCompassOverlay: CompassOverlay? = null
    private var mScaleBarOverlay: ScaleBarOverlay? = null
    private var mRotationGestureOverlay: RotationGestureOverlay? = null
    private var btCenterMap: ImageButton? = null
    private var btFollowMe: ImageButton? = null
    private var lm: LocationManager? = null
    private lateinit var mLocationListener: GpsMyLocationProvider



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
            Toast.makeText(this.activity, "Marker's Listener invoked", Toast.LENGTH_LONG).show()
            true
        }

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /*
        lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        val location = getLocation()
        val senialgps = location!!.accuracy
        Toast.makeText(this.activity,  senialgps.toString() , Toast.LENGTH_LONG).show()


            mLocationRequest = LocationRequest()

            lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?


            if (!lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps()
            }

            addLocationListener()
            val location = getLastKnownLocation()

            val senialgps = location!!.accuracy
            Toast.makeText(this.activity,  senialgps.toString() , Toast.LENGTH_LONG).show()

            //ESTO NO FUNCIONA
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.isCostAllowed = false

            val providerName = lm!!.getBestProvider(criteria, true)
            if(providerName != null){
                Toast.makeText(this.activity,  "NO GPS provider" , Toast.LENGTH_LONG).show()
            }
            //ESTO NO FUNCIONA
            */

        return root
    }
    }




