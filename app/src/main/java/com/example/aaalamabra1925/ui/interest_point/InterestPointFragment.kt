package com.example.aaalamabra1925.ui.interest_point

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.DbManager
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.widget.Toast


/*
This fragment takes care of an interest point view.
It gets a bundle with the ID of the interest point to show information about. Looks for it in the database and updates that information in the view.
 */

class InterestPointFragment : Fragment() {



    var y0_ini = -1.0F
    var y0_fin = -1.0F
    var y1_fin = -1.0F
    var y1_ini = -1.0F
    var y2_fin = -1.0F
    var y2_ini = -1.0F
    var reg = false
    val UMBRAL = 100

    private val mOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            val action = event!!.actionMasked

            // Al pulsar con dos dedos registramos la posición y inicial de cada dedo
            if (event.pointerCount >= 3 && action == MotionEvent.ACTION_POINTER_DOWN) {
                //Toast.makeText(this, "action pointer down", Toast.LENGTH_SHORT).show();
                y0_ini = event.getY(0)
                y1_ini = event.getY(1)
                y2_ini = event.getY(2)
                reg = true
                //Toast.makeText(context, "dentro del touch", Toast.LENGTH_SHORT).show()
            }

            // Si detectamos un movimiento adecuado cambiamos la imagen
            if (action == ACTION_UP && reg) {
                //Toast.makeText(context, "action up", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "action up", Toast.LENGTH_SHORT).show();
                reg = false
                // Movimiento hacia arriba
                if (y0_ini - y0_fin > UMBRAL) {
                    //Toast.makeText(context, "arriba", Toast.LENGTH_SHORT).show();
                    activity?.onBackPressed()

                }

            }

            // Mientras nos movamos actualizamos la posición y final
            if (action == MotionEvent.ACTION_MOVE && reg) {
                //Toast.makeText(this, "action move", Toast.LENGTH_SHORT).show();
                y0_fin = event.getY(0)
            }


            return true
        }

    }

    fun onTouchEvent(event: MotionEvent) : Boolean{
            val action = event!!.actionMasked

            // Al pulsar con dos dedos registramos la posición y inicial de cada dedo
            if (event.pointerCount >= 3 && action == MotionEvent.ACTION_POINTER_DOWN) {
                //Toast.makeText(this, "action pointer down", Toast.LENGTH_SHORT).show();
                y0_ini = event.getY(0)
                y1_ini = event.getY(1)
                y2_ini = event.getY(2)
                reg = true
            }
            // Mientras nos movamos actualizamos la posición y final
            if (action == MotionEvent.ACTION_MOVE && reg) {
                //Toast.makeText(this, "action move", Toast.LENGTH_SHORT).show();
                y0_fin = event.getY(0)
                y1_fin = event.getY(1)
            }
            // Si detectamos un movimiento adecuado cambiamos la imagen
            if (action == MotionEvent.ACTION_POINTER_UP && reg) {
                //Toast.makeText(this, "action up", Toast.LENGTH_SHORT).show();
                reg = false
                // Movimiento hacia arriba
                if (y0_ini - y0_fin > UMBRAL && y1_ini - y1_fin > UMBRAL) {
                    Toast.makeText(this.activity!!, "arriba", Toast.LENGTH_SHORT).show();

                }
                if (y0_fin - y0_ini > UMBRAL && y1_fin - y1_ini > UMBRAL) {
                    Toast.makeText(this.activity!!, "abajo", Toast.LENGTH_SHORT).show();

                }
            }

            return true



    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(com.example.aaalamabra1925.R.layout.fragment_interest_point, container, false)

        // Get textViews.
        val titleView: TextView = root.findViewById(com.example.aaalamabra1925.R.id.title)
        val contentView: TextView = root.findViewById(com.example.aaalamabra1925.R.id.content)

        // Get bundle
        val id = arguments!!.get("id")
        val dbManager = DbManager(context!!)

        // Initialise database cursor and get information.
        val cursor = dbManager.queryById(id as Int)
        if (cursor.moveToFirst()) {
                titleView.text = cursor.getString(cursor.getColumnIndex("Title"))
                contentView.text = cursor.getString(cursor.getColumnIndex("Content"))
        }

        contentView.setOnTouchListener(mOnTouchListener)

        val sensormanager = context!!.getSystemService(SENSOR_SERVICE) as SensorManager?


        return root
    }
}