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

class GestureRecognitionDialog: DialogFragment(){
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor

    // Listener for the accelerometer
    private val mAccelerometerSensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            // TODO Tomar las coordenadas de la brujula y el gps (npi de como hacerlo)
            Log.d("Accelerometer_listener", event.toString())
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.d("Accelerometer_listener", "$sensor - $accuracy")
        }
    }

    override fun onStart() {
        super.onStart()

        if (mAccelerometer != null) {
            mSensorManager.registerListener(mAccelerometerSensorListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    override fun onStop() {
        super.onStop()

        if (mAccelerometer != null) {
            mSensorManager.unregisterListener(mAccelerometerSensorListener)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Get sensor manager
            mSensorManager = it.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            // Get the accelerometer
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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
}
