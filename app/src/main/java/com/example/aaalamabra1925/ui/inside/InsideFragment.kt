package com.example.aaalamabra1925.ui.inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.content.ContextCompat
import com.example.aaalamabra1925.R.*
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.R
import android.widget.LinearLayout


class InsideFragment : Fragment() {
    private lateinit var layout : ConstraintLayout

    private fun dpToPx(context: Context, dp: Int): Int {
        // Reference http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun addFloatingButton(root: View, id : Int ){
        // Doesnt work
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(200, 32, 32, 32)

        val fab = FloatingActionButton(context!!)
        fab.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        fab.setOnClickListener {
            val bundle = bundleOf("id" to id)
            findNavController().navigate(R.id.action_nav_insidemap_to_nav_ip, bundle)
        }

        val linearLayout = root.findViewById<ConstraintLayout>(R.id.layout)
        linearLayout?.addView(fab)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_inside, container, false)
        layout = root.findViewById(R.id.layout) as ConstraintLayout
        super.onCreate(savedInstanceState)

        val id = arguments!!.get("id")
        val dbManager = DbManager(context!!)

        //test fab
        addFloatingButton(root ,0)

        // Looks for IP with locationtype id+1
        val cursor = dbManager.queryByLocationType(id as Int + 1)
        if (cursor.moveToFirst()){
            do{
                val lat = cursor.getDouble(cursor.getColumnIndex("latitude"))
                val long = cursor.getDouble(cursor.getColumnIndex("longitude"))
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                // Use them someway
                addFloatingButton(root ,id)

            }while(cursor.moveToNext())
        }


        if(id == 1){
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, drawable.mapapb))
        }else{
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, drawable.mapacafeteria))
        }

        /*val button = root.findViewById<FloatingActionButton>
        val params = button.layoutParams as MarginLayoutParams
        params.setMargins(activity?.let { dpToPx(it, 8) }!!, activity?.let { dpToPx(it, 10) }!!, 0, 0)
        button.layoutParams = params

        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.activity,  "Marker's Listener invoked" , Toast.LENGTH_LONG).show()
            true
        })*/

        return root
    }
}