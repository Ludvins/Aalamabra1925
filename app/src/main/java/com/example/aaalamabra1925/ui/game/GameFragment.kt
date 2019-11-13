package com.example.aaalamabra1925.ui.game

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aaalamabra1925.R


class GameFragment : Fragment() {

    private var questions = listOf(
        Pair("This one is True", true),
        Pair("This one is false", false),
        Pair("This one is also True", true)


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        quickNoteDialog()
        val remainingQuestions = questions.shuffled()

        val qView = root.findViewById<TextView>(R.id.question_view)

        remainingQuestions.forEach {
            qView.text = it.first
        }



        return root
    }


    private fun quickNoteDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.dialog_game_explanation, null)
        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle("New Task")
        dialogBuilder.setPositiveButton("Okay") { _, _ ->
            //pass
        }
        val b = dialogBuilder.create()
        b.show()
    }
}
