package com.example.aaalamabra1925.ui.ipmenu

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.R
import kotlinx.android.synthetic.main.fragment_ipmenu.*


class InteresPointMenuFragment : Fragment() {

    private val list = mutableListOf<InterestPoint>()
    private var listAdapter : IPAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ipmenu, container, false)

        val mainList = this.list_view
        listAdapter = IPAdapter(this.context!!, list)

        //TODO Fix this. Mainlist seems to be null.
        mainList.adapter = listAdapter

        list.add(InterestPoint(0, "test", "test2"))
        listAdapter?.notifyDataSetChanged()

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