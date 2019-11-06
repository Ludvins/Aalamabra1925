package com.example.aaalamabra1925

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.hardware.Sensor
import androidx.fragment.app.DialogFragment
import android.hardware.SensorManager
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log


class MainActivity : AppCompatActivity() {

    class GestureRecognitionDialog: DialogFragment() {
        private lateinit var mSensorManager: SensorManager
        private lateinit var mAccelerometer: Sensor
        private lateinit var mContext : Context

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
                //setContentView(R.layout.activity_sensor);
                //mSensorManager = (SensorManager) (it.getSystemService(Context.SENSOR_SERVICE))
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

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var gestureRecognitionDialog: GestureRecognitionDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            gestureRecognitionDialog.show(supportFragmentManager, "gestures")
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
