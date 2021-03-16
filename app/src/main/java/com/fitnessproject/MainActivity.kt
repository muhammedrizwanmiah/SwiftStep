package com.fitnessproject

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fitnessproject.fragments.ProfileFragment
import com.fitnessproject.fragments.StepsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_steps.*

class MainActivity : AppCompatActivity() {

//    private var sensorManager: SensorManager? = null
//
//    private var running = false
//    private var totalSteps = 0f
//    private var previousTotalSteps = 0f
//
//    private val stepsTakenTextView: TextView = findViewById<TextView>(R.id.tvStepsTaken)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val profileFragment = ProfileFragment()
        val stepsFragment = StepsFragment()

        makeCurrentFragment(profileFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_profile -> makeCurrentFragment(profileFragment)
                R.id.navigation_steps -> makeCurrentFragment(stepsFragment)
            }
            true
        }

//        loadData()
//        resetSteps()
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

//    override fun onResume() {
//        super.onResume()
//        running = true
//        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if(stepSensor == null){
//            Toast.makeText(this, "there is no step sensor",
//                Toast.LENGTH_SHORT).show()}
//        else{
//            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
//        }
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        if(running){
//            totalSteps = event!!.values[0]
//            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
//            tvStepsTaken.text = ("$currentSteps")
//
//            circularProgressBar.apply{
//                setProgressWithAnimation(currentSteps.toFloat())
//            }
//        }
//    }
//
//    fun resetSteps() {
//
//        stepsTakenTextView.setOnClickListener {
//            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
//        }
//
//        stepsTakenTextView.setOnLongClickListener {
//            previousTotalSteps = totalSteps
//            tvStepsTaken.text = 0.toString()
//            saveData()
//
//            true
//        }
//
//    }
//
//    private fun saveData() {
//        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putFloat("key1", previousTotalSteps)
//        editor.apply()
//    }
//
//    private fun loadData() {
//        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        val savedNumber = sharedPreferences.getFloat("key1", 0f)
//        Log.d("StepsFragment", "$savedNumber")
//        previousTotalSteps = savedNumber
//
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//    }

}