package com.example.aaalamabra1925.ui.inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.widget.Toast
import com.example.aaalamabra1925.R.id.ip1
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import com.example.aaalamabra1925.R.*
import android.content.Context


class InsideFragment : Fragment() {

    private lateinit var insideViewModel: InsideViewModel

    fun dpToPx(context: Context, dp: Int): Int {
        // Reference http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insideViewModel =
            ViewModelProviders.of(this).get(InsideViewModel::class.java)
        val root = inflater.inflate(layout.fragment_inside, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_send)
        insideViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        super.onCreate(savedInstanceState)

        val id = arguments!!.get("id")

        if(id == 1){
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, com.example.aaalamabra1925.R.drawable.mapapb))

        }else{
            root.setBackgroundDrawable(ContextCompat.getDrawable(this.context!!, com.example.aaalamabra1925.R.drawable.mapacafeteria))

        }

        val button = root.findViewById<FloatingActionButton>(ip1)
        val params = button.layoutParams as MarginLayoutParams
        params.setMargins(activity?.let { dpToPx(it, 8) }!!, activity?.let { dpToPx(it, 10) }!!, 0, 0)
        button.layoutParams = params

        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.activity,  "Marker's Listener invoked" , Toast.LENGTH_LONG).show()
            true
        })

        return root
    }
}