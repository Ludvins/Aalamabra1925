package com.example.aaalamabra1925.ui.inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.R
import android.widget.RelativeLayout
import android.R.drawable
import android.content.res.ColorStateList

// Delete some deprecation warnings.
@Suppress("DEPRECATION")

/*
This fragment takes care of the inner map view. Given a map id, it set the view's background to the corresponding image.
Also gets the corresponding interest points from the database and adds them as floating buttons on the view.
 */

class InnerMapFragment : Fragment() {

    private lateinit var layout : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_inside, container, false)
        layout = root.findViewById(R.id.layout) as RelativeLayout
        super.onCreate(savedInstanceState)

        // Gets the map id. this is used to set the correct map.
        val mid = arguments!!.get("id")

        // Get database manager
        val dbManager = DbManager(context!!)

        // Get a cursor over the corresponding interest points.
        val cursor = dbManager.queryByLocationType(mid as Int)
        if (cursor.moveToFirst()){
            do{
                val lat = cursor.getDouble(cursor.getColumnIndex("Latitude"))
                val long = cursor.getDouble(cursor.getColumnIndex("Longitude"))
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                // For each interest point, add a floating button on the view.
                addFloatingButton(root ,id, long.toInt(), lat.toInt())

            }while(cursor.moveToNext())
        }

        // Depending on the map id, set de background to one image or another.
        if(mid == 1)
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.puertadelajusticia))
        else
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.palaciocv))


        return root
    }

    // This function adds a floating button using lat and long as margins.
    private fun addFloatingButton(root: View, id : Int, long:Int, lat:Int){

        // Create the fab
        val fab = FloatingActionButton(context!!)

        // Create a layout.
        val rel = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Use lat and long to position the layout.
        rel.setMargins(lat, long, 0, 0)

        // Set the layout to the fab
        fab.layoutParams = rel

        // Set fab icon, size, colors and no shadow efect.
        fab.setImageResource(drawable.ic_dialog_info)
        fab.size = FloatingActionButton.SIZE_MINI
        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        fab.elevation = 0f
        fab.compatElevation = 0f

        // Override fab click listener with the corresponding call to the interest point it represents.
        fab.setOnClickListener {
            val bundle = bundleOf("id" to id)
            findNavController().navigate(R.id.action_nav_inner_map_to_nav_ip, bundle)
        }

        // Add the fab to the view.
        val linearLayout = root.findViewById<RelativeLayout>(R.id.layout)
        linearLayout?.addView(fab)
    }
}