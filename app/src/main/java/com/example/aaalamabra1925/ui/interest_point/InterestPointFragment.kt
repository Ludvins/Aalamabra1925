package com.example.aaalamabra1925.ui.interest_point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aaalamabra1925.R

class InterestPointFragment : Fragment() {

    private lateinit var interestPointViewModel: InterestPointViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        interestPointViewModel =
            ViewModelProviders.of(this).get(InterestPointViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_interest_point, container, false)

        val titleView: TextView = root.findViewById(R.id.title)
        val contentView: TextView = root.findViewById(R.id.content)

        interestPointViewModel.title.observe(this, Observer {
            titleView.text = it
        })

        interestPointViewModel.content.observe(this, Observer {
            contentView.text = it
        })

        return root
    }
}