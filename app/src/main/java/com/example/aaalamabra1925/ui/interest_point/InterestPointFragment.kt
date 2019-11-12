package com.example.aaalamabra1925.ui.interest_point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aaalamabra1925.DbManager
import com.example.aaalamabra1925.R

class InterestPointFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_interest_point, container, false)
        val titleView: TextView = root.findViewById(R.id.title)
        val contentView: TextView = root.findViewById(R.id.content)

        val id = arguments!!.get("id")
        val dbManager = DbManager(context!!)
        val cursor = dbManager.queryById(id as Int)

        if (cursor.moveToFirst()) {
                titleView.text = cursor.getString(cursor.getColumnIndex("Title"))
                contentView.text = cursor.getString(cursor.getColumnIndex("Content"))
        }

        return root
    }
}