package com.example.aaalamabra1925.ui.ipmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aaalamabra1925.InterestPoint
import com.example.aaalamabra1925.R


class IPAdapter(context: Context, private val dataSource: MutableList<InterestPoint>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = this.inflater.inflate(R.layout.ip_list_item, parent, false)
        val titleTextView = rowView.findViewById(R.id.title) as TextView
        val ip = getItem(position) as InterestPoint

        titleTextView.text = ip.title

        return rowView
    }
}
