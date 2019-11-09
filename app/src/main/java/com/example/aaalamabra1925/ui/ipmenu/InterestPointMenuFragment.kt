package com.example.aaalamabra1925.ui.ipmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.InterestPoint
import com.example.aaalamabra1925.R
import com.example.aaalamabra1925.ui.interest_point.InterestPointFragment


class InterestPointMenuFragment : Fragment() {

    private val list = mutableListOf<InterestPoint>()
    private var listAdapter : IPAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ipmenu, container, false)

        val mainList = root.findViewById<ListView>(R.id.list_view)
        listAdapter = IPAdapter(this.context!!, list)

        mainList.adapter = listAdapter

        mainList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            findNavController().navigate(R.id.action_nav_ipmenu_to_nav_ip)
            val ip = list[position]

            //fm!!.beginTransaction().add(R.layout.content_main, InterestPointFragment()).commit()
        }

        loadQueryAll()
        return root
    }

    override fun onResume() {
        super.onResume()
        loadQueryAll()
    }

    private fun loadQueryAll() {
        // Fills notesList with database.

        val dbManager = DbManager(this.context!!)
        val cursor = dbManager.queryAll()

        list.clear()
        if (cursor.moveToFirst()) {
            do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val title = cursor.getString(cursor.getColumnIndex("Title"))
                    val content = cursor.getString(cursor.getColumnIndex("Content"))
                    list.add(InterestPoint(id, title, content))
            } while (cursor.moveToNext())
        }
    }

}