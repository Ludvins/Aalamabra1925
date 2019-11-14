package com.example.aaalamabra1925.ui.inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.R

class InnerMapListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_inner_map_list, container, false)
        val list = listOf("Cafeteria", "El otro")
        val listView = root.findViewById<ListView>(R.id.list_view)
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val bundle = bundleOf("id" to position+1)
            findNavController().navigate(R.id.action_nav_inner_maps_list_to_nav_insidemap, bundle)
        }
        return root
    }


    override fun onResume() {
        super.onResume()
    }

}