package com.example.aaalamabra1925.ui.interest_point

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.DbManager
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.R


/*
This fragment takes care of an interest point view.
It gets a bundle with the ID of the interest point to show information about. Looks for it in the database and updates that information in the view.
 */
class InterestPointFragment : Fragment() {

    // Initialise variables por 3 finger gesture recognition
    var y0_ini = -1.0F
    var y0_fin = -1.0F
    var y1_fin = -1.0F
    var y2_fin = -1.0F
    var y1_ini = -1.0F
    var y2_ini = -1.0F
    var reg = false

    private val mOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            val action = event!!.actionMasked
            // When 3 fingers appear, register their initial position.
            if (event.pointerCount >= 3 && action == MotionEvent.ACTION_POINTER_DOWN) {
                y0_ini = event.getY(0)
                y1_ini = event.getY(1)
                y2_ini = event.getY(2)
                reg = true
            }
            // When fingers are released
            if (action == ACTION_UP && reg) {
                reg = false
                // Check if pinch gesture is done.
                if (
                    (y0_ini < y0_fin  && y1_ini < y1_fin  && y2_ini > y2_fin) ||
                    (y0_ini > y0_fin  && y1_ini < y1_fin  && y2_ini < y2_fin) ||
                    (y0_ini < y0_fin  && y1_ini > y1_fin  && y2_ini < y2_fin)
                        ) {
                    findNavController().navigate(R.id.action_nav_ip_to_nav_home, null)
                }
            }
            // Register position while the fingers are moving
            if (action == MotionEvent.ACTION_MOVE && reg && event.pointerCount >= 3) {
                y0_fin = event.getY(0)
                y1_fin = event.getY(1)
                y2_fin = event.getY(2)
            }

            return true
        }

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

        return root
    }
}