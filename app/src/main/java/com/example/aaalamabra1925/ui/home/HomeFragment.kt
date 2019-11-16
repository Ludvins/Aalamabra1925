package com.example.aaalamabra1925.ui.home

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.compass.CompassOverlay
import android.widget.Toast
import com.example.aaalamabra1925.DbManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.GestureRecognitionDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import kotlin.math.min
import kotlin.math.pow


class HomeFragment : Fragment() {

    private var mLocationOverlay: MyLocationNewOverlay? = null
    private var mCompassOverlay: CompassOverlay? = null
    private var mScaleBarOverlay: ScaleBarOverlay? = null
    private var mRotationGestureOverlay: RotationGestureOverlay? = null
    private var mLocationManager: LocationManager? = null
    private lateinit var mapView: MapView

    private val MODO_ALHAMBRA = true

    private val PUERTA_CAFETERIA_1 = GeoPoint(37.19701, -3.6243)
    private val PUERTA_CAFETERIA_2 = GeoPoint(37.197152, -3.6247)

    private val PUERTA_AULARIO_1 = GeoPoint(37.19725, -3.624225)
    private val PUERTA_AULARIO_2 = GeoPoint(37.1973, -3.6247)

    private val PUERTA_JUSTICIA_1 = GeoPoint(37.176094, -3.590247)
    private val PUERTA_JUSTICIA_2 = GeoPoint(37.176187, -3.590358)

    private val PUERTA_CARLOS_V = GeoPoint(37.176856, -3.590191)

    private var change = true

    private var gestureRecognitionDialog = GestureRecognitionDialog()

    private val mLocationListener = object : LocationListener{
        override fun onLocationChanged(location: Location?) {
            Log.d("Home fragment", "Change $change")
            if (location != null && change){
                // TODO  This doesn't work sometimes
                val senialgps = location.accuracy
                Toast.makeText(context,  senialgps.toString() , Toast.LENGTH_LONG).show()
                Log.d("Home fragment", "Location: $senialgps")

                if(senialgps >= 22.00F && change){
                    change = false
                    val id = nearDoor(location)
                    val bundle = bundleOf("id" to id)
                    findNavController().navigate(R.id.action_nav_home_to_nav_inner_map, bundle)
                }
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}

        fun nearDoor(location: Location) : Int{
            if(MODO_ALHAMBRA){
                val dis1 = ((location.longitude - PUERTA_JUSTICIA_1.longitude).pow(2) + (location.altitude - PUERTA_JUSTICIA_1.altitude).pow(2))
                val dis2 = ((location.longitude - PUERTA_JUSTICIA_2.longitude).pow(2) + (location.altitude - PUERTA_JUSTICIA_2.altitude).pow(2))
                val distanciaJusticia = min(dis1, dis2)


                val distanciaCarlosV = ((location.longitude - PUERTA_CARLOS_V.longitude).pow(2) + (location.altitude - PUERTA_CARLOS_V.altitude).pow(2))

                return if (distanciaJusticia < distanciaCarlosV) 1 else 2

            }else{

                var dis1 = ((location.longitude - PUERTA_CAFETERIA_1.longitude).pow(2) + (location.altitude - PUERTA_CAFETERIA_1.altitude).pow(2))
                var dis2 = ((location.longitude - PUERTA_CAFETERIA_2.longitude).pow(2) + (location.altitude - PUERTA_CAFETERIA_2.altitude).pow(2))
                val distanciaCaf = min(dis1, dis2)
                Log.d("Home fragment", "Distancia Caf $distanciaCaf")


                dis1 = ((location.longitude - PUERTA_AULARIO_1.longitude).pow(2) + (location.altitude - PUERTA_AULARIO_1.altitude).pow(2))
                dis2 = ((location.longitude - PUERTA_AULARIO_2.longitude).pow(2) + (location.altitude - PUERTA_AULARIO_2.altitude).pow(2))
                val distanciaAul = min(dis1, dis2)
                Log.d("Home fragment", "Distancia Aul $distanciaAul")

                return if (distanciaAul < distanciaCaf) 1 else 2
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Button
        val fab: FloatingActionButton = root.findViewById(R.id.detection)
        fab.setOnClickListener {
            gestureRecognitionDialog.show(activity!!.supportFragmentManager, "gestures")
        }

        mapView = root.findViewById(R.id.openmapview)
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


        val dbManager = DbManager(context!!)

        val cursor = dbManager.queryByLocationType(0)
        if (cursor.moveToFirst()) {
            do {
                addMarker(
                    cursor.getDouble(cursor.getColumnIndex("Latitude")),
                    cursor.getDouble(cursor.getColumnIndex("Longitude")),
                    cursor.getInt(cursor.getColumnIndex("Id"))
                )
            } while (cursor.moveToNext())
        }

        super.onCreate(savedInstanceState)

        change = true

        mLocationManager = context!!.getSystemService(LOCATION_SERVICE) as LocationManager?

        if (ContextCompat.checkSelfPermission(activity as Context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 1.0F, mLocationListener, null)
        }

        return root
    }

    private fun addMarker(lat: Double, long: Double, id: Int){
        val test = Marker(mapView)
        test.position = GeoPoint(lat, long)
        test.textLabelFontSize = 40
        //test.setTextIcon(id.toString())
        test.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
        //test.icon = resources.getDrawable(R.drawable.ic_menu_compass)
        test.infoWindow = null
        mapView.overlays.add(test)

        test.setOnMarkerClickListener { _, _ ->
            val bundle = bundleOf("id" to id)
            findNavController().navigate(R.id.action_nav_home_to_nav_ip, bundle)
            true
        }
    }
}




