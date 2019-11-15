package com.example.aaalamabra1925.ui.ip_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.ui.interest_point.InterestPoint
import com.example.aaalamabra1925.R

/*
This fragments corresponds to a list of Interest Points.
It is included in the navigation bar.
Uses a specific BaseAdapter to handle the list view 'IPAdapter'.

Each interest point is clickable and links with the corresponding view with its information.
 */
class InterestPointMenuFragment : Fragment() {

    // List of interest points.
    private val list = mutableListOf<InterestPoint>()
    // Adapter
    private var listAdapter : IPAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ip_menu, container, false)

        // ListView
        val mainList = root.findViewById<ListView>(R.id.list_view)
        // Set the custom adapter to the listView
        listAdapter = IPAdapter(this.context!!, list)
        mainList.adapter = listAdapter

        // Set the listener for each element in the listView
        mainList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Create a bundle with "id". The InterestPoint fragment will handle it and show the corresponding information from de database.
            val bundle = bundleOf("id" to list[position].id)
            // Uses navController action to navigate between fragments.
            findNavController().navigate(R.id.action_nav_ip_menu_to_nav_ip, bundle)
        }

        // Fills listView.
        loadQueryAll()
        return root
    }

    // listView must be filled on resume.
    override fun onResume() {
        super.onResume()
        loadQueryAll()
    }

    /*
    This function creates a cursor over all interest points in the database and inserts them in the mutable list.
     */
    private fun loadQueryAll() {

        val dbManager = DbManager(this.context!!)
        val cursor = dbManager.queryAll()

        list.clear()
        if (cursor.moveToFirst()) {
            do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val title = cursor.getString(cursor.getColumnIndex("Title"))
                    val content = cursor.getString(cursor.getColumnIndex("Content"))
                    list.add(
                        InterestPoint(
                            id,
                            title,
                            content
                        )
                    )
            } while (cursor.moveToNext())
        }
    }

}