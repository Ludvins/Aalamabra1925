package com.example.aaalamabra1925.ui.ipmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.R

class InteresPointMenuFragment : Fragment() {

    private val list = mutableListOf(InterestPoint(0, "test", "test"))
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

        return root
    }

    override fun onResume() {
        super.onResume()
        //loadQueryAll()
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