package com.example.aaalamabra1925

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.R.attr.name
import android.R.attr.name
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.jar.Manifest
import android.R.attr.name
import androidx.core.graphics.rotationMatrix
import java.lang.Math.*


class GestureRecognitionDialog: DialogFragment(){
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mMagnetometer: Sensor
    private lateinit var mLocationManager: LocationManager
    private var azimuth: Float? = null
    private var latitude : Double? = null
    private var longitude: Double? = null
    private var searching : Boolean? = null
    private var mGravity: FloatArray? = null
    private var mGeomagnetic: FloatArray? = null

    private val mLocationListener = object : LocationListener{
        override fun onLocationChanged(location: Location?) {
            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            val dbManager = DbManager(context!!)
            val cursor = dbManager.queryByLocationType(0)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val ip_lat = cursor.getFloat(cursor.getColumnIndex("Latitude"))
                    val ip_long = cursor.getFloat(cursor.getColumnIndex("Longitude"))

                    val v = floatArrayOf(0 as Float,1 as Float)
                    var rotation_m = rotationMatrix(azimuth!!, 0 as Float,0 as Float)
                    rotation_m.mapVectors(v)
                    rotation_m = rotationMatrix((PI/2) as Float, 0 as Float, 0 as Float)
                    rotation_m.mapVectors(v)

                } while (cursor.moveToNext())
            }

            Log.d("Gesture_Dialog", "Location:" + latitude + ", " + longitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    // Listener for the accelerometer
    private val mPositionListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values
            if (mGravity != null && mGeomagnetic != null) {
                val R = FloatArray(9)
                val I = FloatArray(9)
                val success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)
                if (success) {
                    val orientation = FloatArray(3)
                    // orientation contains: azimut(0), pitch(1) and roll(2)
                    SensorManager.getOrientation(R, orientation)

                    if (orientation[1] > 0 && searching!!){
                        searching = false
                        azimuth = orientation[0]
                        Toast.makeText(activity, "Buscando punto de interés", Toast.LENGTH_LONG).show()

                        if (ContextCompat.checkSelfPermission(activity as Context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                            mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null)
                            Toast.makeText(activity, "Buscando punto de interés...", Toast.LENGTH_LONG).show()
                        }

                    }

                }
            }

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Get sensor manager
            mSensorManager = it.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            // Get the sensors necessary for position
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

            // Get location manager
            mLocationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Apunta al punto de interés que quieras conocer")
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { _, _->
                        // User cancelled the dialog
                    })

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        mSensorManager.registerListener(mPositionListener, mAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager.registerListener(mPositionListener, mMagnetometer,
            SensorManager.SENSOR_DELAY_NORMAL)
        searching = true
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(mPositionListener)
    }

}
