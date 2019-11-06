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
import java.lang.Math.abs


class GestureRecognitionDialog: DialogFragment(){
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mMagnetometer: Sensor

    // Listener for the accelerometer
    private val mPositionListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            var mGravity: FloatArray? = null
            var mGeomagnetic: FloatArray? = null

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
                    // orientation contains: azimut, pitch and roll
                    SensorManager.getOrientation(R, orientation)
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
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(mPositionListener)
    }

}
