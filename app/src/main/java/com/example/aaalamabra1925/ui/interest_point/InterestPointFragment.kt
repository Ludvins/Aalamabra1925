package com.example.aaalamabra1925.ui.interest_point

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


/*
This fragment takes care of an interest point view.
It gets a bundle with the ID of the interest point to show information about. Looks for it in the database and updates that information in the view.
 */

class InterestPointFragment : Fragment() {


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


        return root
    }
}