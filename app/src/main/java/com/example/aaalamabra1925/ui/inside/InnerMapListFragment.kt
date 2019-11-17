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


// This fragment shows a list of Strings, each of them symbolises an inner map. The Click listener is overloaded so it changes the view to the corresponding view.

class InnerMapListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_inner_map_list, container, false)

        // Initialises the list
        val list = listOf("Puerta de la Justicia", "Palacio de Carlos V")

        // Get listView and Adapter.
        val listView = root.findViewById<ListView>(R.id.list_view)
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        // Overload ClickListener
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // The map position on the list corresponds to its identifier + 1.
            val bundle = bundleOf("id" to position+1)
            findNavController().navigate(R.id.action_nav_inner_maps_list_to_nav_inner_map, bundle)
        }
        return root
    }
}