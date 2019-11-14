package com.example.aaalamabra1925.ui.game

import android.app.AlertDialog
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.aaalamabra1925.R
import kotlinx.android.synthetic.main.dialog_game_explanation.*

class GameFragment : Fragment() {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private var points = 0
    private var currentQuestion = 0
    private lateinit var textView: TextView
    private var unmannaged_gesture = false

    private val mAccelerometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?){
            val mAcceleration = event!!.values
            if (!unmannaged_gesture){
                unmannaged_gesture = true
                if (kotlin.math.abs(mAcceleration[2]) > 2F){
                    Log.d("Game_frag", "Yes gesture!")
                    manageAnswer(questions[currentQuestion].second, true)
                }
                else if (kotlin.math.abs(mAcceleration[0]) > 2F) {
                    Log.d("Game_frag", "No gesture!")
                    manageAnswer(questions[currentQuestion].second, false)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSensorManager = activity!!.getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    override fun onStart() {
        super.onStart()
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(mAccelerometerListener)
    }

    private var questions = listOf(
        Pair("This one is True", true),
        Pair("This one is false", false),
        Pair("This one is also True", true)
    ).shuffled()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        explainDialog()
        textView = root.findViewById(R.id.question_view)
        textView.text = questions.first().first
        return root
    }

    private fun manageAnswer(a:Boolean, b:Boolean){
        if (a==b){
            Toast.makeText(context!!, "Correct!!", Toast.LENGTH_LONG).show()
            points++
        }
        else{
            Toast.makeText(context!!, "Wrong!!", Toast.LENGTH_LONG).show()
        }

        if (currentQuestion < questions.size-1) {
            currentQuestion++
            textView.text = questions[currentQuestion].first
            view!!.invalidate()
            Thread.sleep(1000)
            unmannaged_gesture = false
        }
        else {
            textView.text = "Congratulations, you answered $points out of ${questions.size} correctly!"
            // TODO Dont listen to gestures again!
        }

    }


    private fun explainDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.dialog_game_explanation, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Game instructions")
        dialogBuilder.setPositiveButton("Okay") { _, _ ->
            //pass
        }
        val b = dialogBuilder.create()
        b.show()
    }

}
