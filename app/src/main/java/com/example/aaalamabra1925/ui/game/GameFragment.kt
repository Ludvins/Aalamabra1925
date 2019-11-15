package com.example.aaalamabra1925.ui.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.aaalamabra1925.R

/*
This fragment handles the game section.
It consists of a view with several questions that the user must answer using a gesture.
All the gesture caption will be handle in this fragment so it is fully independent from the rest.
 */
class GameFragment : Fragment() {

    // Sensors points and questions initialization.
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private var points = 0
    private var currentQuestion = 0

    private lateinit var textView: TextView
    private lateinit var progressBar : ProgressBar

    // Seconds user has for answering all the questions
    private val countDownMillis = 30000
    // Manages time user has for answering questions
    private val mCountDownTimer = object : CountDownTimer(countDownMillis.toLong(), 200){
        // Every 200 millis updates progress bar
        override fun onTick(millisUntilFinished: Long) {
            val progress = (countDownMillis - millisUntilFinished.toInt()).toFloat()/countDownMillis * 100
            progressBar.progress = progress.toInt()
        }

        /*
        When count down finishes, stops listener and display the number of correctly answered
        questions
        */
        override fun onFinish() {
            progressBar.progress = 100
            mSensorManager.unregisterListener(mAccelerometerListener)
            textView.text = "Congratulations, you answered $points questions correctly!"
            view!!.invalidate()
        }
    }

    // Sets the accelerometer listener.
    private val mAccelerometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        /*
        This method is called whenever the sensor changes and the listener is registered.
         */
        override fun onSensorChanged(event: SensorEvent?){
            // Get acceleration value.
            val mAcceleration = event!!.values

            // If a considerable acceleration is detected back and forth, a "yes" answer is considered.
            if (kotlin.math.abs(mAcceleration[2]) > 2F){
                Log.d("Game_frag", "Yes gesture!")
                // Unregister the listener so no more gestures are captured until the next question is shown.
                mSensorManager.unregisterListener(this)
                // Manages the answer.
                manageAnswer(questions[currentQuestion].second, true)
            }
            // Does the same with an acceleration from side to side.
            else if (kotlin.math.abs(mAcceleration[0]) > 2F) {
                Log.d("Game_frag", "No gesture!")
                mSensorManager.unregisterListener(this)
                manageAnswer(questions[currentQuestion].second, false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize sensors.
        mSensorManager = activity!!.getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    override fun onStart() {
        super.onStart()
        // Register listener on start.
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        // Unregister listener on stop.
        mSensorManager.unregisterListener(mAccelerometerListener)
    }

    // Initialize questions and shuffle them.
    private var questions = listOf(
        Pair("¿Es cierto que el significado de Alhambra en castellano es \"roja\"?", true),
        Pair("¿Es cierto que la Alhambra es un reloj solar?", true),
        Pair("¿Es cierto que el patio de los Leones fue construido en el siglo XV?", false),
        Pair("¿Es cierto que la Alhambra es el monumento más visitado de España?", false),
        Pair("¿Es cierto que hubo una carta de amor escondida durante 92 años en sus muros?", true),
        Pair("¿Es cierto que Isabel la Católica estuvo enterrada en la Alhambra?", true),
        Pair("¿Es cierto que la Alhambra empezó a construirse en el siglo XI?", false)
    ).shuffled()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        explainDialog()
        textView = root.findViewById(R.id.question_view)
        progressBar = root.findViewById(R.id.timer_bar)

        textView.text = questions.first().first
        return root
    }

    /*
     This function manages the answer given by the gesture listeners.
     Shows a Toast telling whether the answer was right or wrong.
     Changes the question.
     Adds a 1 second sleep so no gestures are recognized while reading the question.

     If no more questions are available, total score is shown.
     */

    @SuppressLint("SetTextI18n")
    private fun manageAnswer(a:Boolean, b:Boolean){
        if (a == b){
            Toast.makeText(context!!, "Correct!!", Toast.LENGTH_LONG).show()
            points++
        }
        else{
            Toast.makeText(context!!, "Wrong!!", Toast.LENGTH_LONG).show()
        }

        if (currentQuestion < questions.size-1) {
            currentQuestion++
            textView.text = questions[currentQuestion].first
            Thread.sleep(1000)
            mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
        else {
            textView.text = "Congratulations, you answered $points out of ${questions.size} correctly!"
            mCountDownTimer.cancel()
        }

        view!!.invalidate()
    }


    // Inflates a dialog where the game instructions are shown.
    private fun explainDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.dialog_game_explanation, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Game instructions")
        dialogBuilder.setNegativeButton("Okay") { _, _ ->
            mCountDownTimer.start()
        }
        val b = dialogBuilder.create()
        b.show()
    }

}
