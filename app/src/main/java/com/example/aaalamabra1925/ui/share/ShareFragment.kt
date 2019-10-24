package com.example.aaalamabra1925.ui.share

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aaalamabra1925.InteresPoint
import com.example.aaalamabra1925.NoteDbManager
import com.example.aaalamabra1925.R

class ShareFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    private val notesList = mutableListOf<InteresPoint>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val textView: TextView = root.findViewById(R.id.text_share)
        shareViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val dbManager = NoteDbManager(this.context!!)
        val values = ContentValues()

        values.put("Id", 123)
        values.put("Title", "Alhamabra")
        values.put("Content", "Información sobre la Alhamabra")

        dbManager.insert(values)

        return root
    }

    private fun loadQueryAll() {
        // Fills notesList with database.

        val dbManager = NoteDbManager(this.context !!)
        val cursor = dbManager.queryAll()

        notesList.clear()
        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))

                notesList.add(InteresPoint(id, title, content))

            } while (cursor.moveToNext())
        }
    }
}