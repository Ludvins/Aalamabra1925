package com.example.aaalamabra1925.ui.inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.widget.Button
import android.widget.Toast
import com.example.aaalamabra1925.R.id.ip1
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.R.id.edit
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.aaalamabra1925.R
import com.example.aaalamabra1925.R.*


class InsideFragment : Fragment() {

    private lateinit var insideViewModel: InsideViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insideViewModel =
            ViewModelProviders.of(this).get(InsideViewModel::class.java)
        val root = inflater.inflate(layout.fragment_inside, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_send)
        insideViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        super.onCreate(savedInstanceState)

        root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.mapapb))

        val id = arguments!!.get("id")


        val button = root.findViewById<FloatingActionButton>(ip1)
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.activity,  "Marker's Listener invoked" , Toast.LENGTH_LONG).show()
            true
        })

        return root
    }
}