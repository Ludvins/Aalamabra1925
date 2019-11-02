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


class InsideFragment : Fragment() {

    private lateinit var insideViewModel: InsideViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insideViewModel =
            ViewModelProviders.of(this).get(InsideViewModel::class.java)
        val root = inflater.inflate(com.example.aaalamabra1925.R.layout.fragment_inside, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_send)
        insideViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        super.onCreate(savedInstanceState)

        val button = root.findViewById<FloatingActionButton>(ip1)
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.activity,  "Marker's Listener invoked" , Toast.LENGTH_LONG).show()
            true
        })

        return root
    }
}