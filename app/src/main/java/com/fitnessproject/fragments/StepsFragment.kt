package com.fitnessproject.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import com.fitnessproject.R
import com.fitnessproject.bmiCalculator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_steps.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class StepsFragment : Fragment(), SensorEventListener {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    //sensor code

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private var currentStepsInt = 0

    var distanceRan = 0.0

    val sdf = SimpleDateFormat("dd/MM/yyyy")
    var currentDate = sdf.format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        //sensor code
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        tvTime.text = "Time: " + "$currentDate"
        tvStepsTaken.text = currentStepsInt.toString()

        circularProgressBar.apply{
            setProgressWithAnimation(currentStepsInt.toFloat(), 1500)
        }

        loadData()
        resetSteps()
        loadPage()
    }

    private fun loadPage() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //calc stride length
                val heightInCM = snapshot.child("height").value.toString().toDouble()
                val strideLength = heightInCM * 0.43
                val stepsInCM = (strideLength * tvStepsTaken.text.toString().toDouble()) / 100000
                val distance = Math.round(stepsInCM * 100.0) / 100.0

                tvDistance.text = "Distance " + distance.toString() + " km"

                if(tvStepGoal == null || tvStepsTaken == null || tvDistance == null){
                    return
                }
                tvStepGoal.text = "/" + snapshot.child("stepgoal").value.toString()
                circularProgressBar.progressMax = snapshot.child("stepgoal").value.toString().toFloat()

                yourBMItext.text = snapshot.child("BMI").value.toString()

                yourHeightText.text = snapshot.child("height").value.toString() + " CM"
                yourWeightText.text = snapshot.child("weight").value.toString() + " KG"
                //logic for colour text changing based on BMI number

                if (snapshot.child("BMI").value.toString().toDouble() >= 18.5 && snapshot.child("BMI").value.toString().toDouble() <= 25.0){
                    yourBMItext.setTextColor(Color.parseColor("#66bb6a")) //if healthy weight, set text to green
                }
                else if (snapshot.child("BMI").value.toString().toDouble() < 18.5){
                    yourBMItext.setTextColor(Color.parseColor("#ffee58")) //if underweight, set text to yellow
                }
                else if (snapshot.child("BMI").value.toString().toDouble() > 25 && snapshot.child("BMI").value.toString().toDouble() <= 30 ){
                    yourBMItext.setTextColor(Color.parseColor("#ffb300")) //if overweight, set text to orange
                }
                else {
                    yourBMItext.setTextColor(Color.parseColor("#ef5350")) //if obese, else set to red
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        userDetailsHeadingSteps.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), bmiCalculator::class.java)
            startActivity(intent)
        }

//        userDetailsHeadingSteps.setOnClickListener {
//
//            if (bottomRow.visibility == View.VISIBLE){
//            bottomRow.visibility = View.GONE
//            } else {
//                bottomRow.visibility = View.VISIBLE
//            }
//        }

    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(requireContext(), "there is no step sensor",
                Toast.LENGTH_SHORT).show()}
        else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        //check if date has changed
        isDateChanged()

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //calc stride length
                val heightInCM = snapshot.child("height").value.toString().toDouble()
                val strideLength = heightInCM * 0.43
                val stepsInCM = (strideLength * tvStepsTaken.text.toString().toDouble()) / 100000
                val distance = Math.round(stepsInCM * 100.0) / 100.0

                tvDistance.text = "Distance " + distance.toString() + " km"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        if (tvStepsTaken == null){
            return
        }

        if(running){
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            tvStepsTaken.text = ("$currentSteps")
            currentStepsInt = currentSteps.toString().toInt()

            circularProgressBar.apply{
                setProgressWithAnimation(currentStepsInt.toFloat(), 1500)
            }
        }

    }

    private fun isDateChanged() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateComparison = sdf.format(Date())

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(currentDate == dateComparison){
                    return
                }else{
                    currentDate = dateComparison
                    userreference?.child("step_data/$currentDate")?.setValue(tvStepsTaken.text.toString()) //save steps to firebase
                    previousTotalSteps = totalSteps
                    tvStepsTaken.text = 0.toString() //reset values to 0
                    currentStepsInt = 0
                    circularProgressBar.apply{
                        setProgressWithAnimation(0f, 1500)
                    } //do animation for reset
                    saveData()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun resetSteps() {
        tvStepsTaken.setOnClickListener {
            Toast.makeText(requireContext(), "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tvStepsTaken.setOnLongClickListener {
            previousTotalSteps = totalSteps
            tvStepsTaken.text = 0.toString()
            currentStepsInt = 0
            circularProgressBar.apply{
                setProgressWithAnimation(0f, 1500)
            }
            saveData()
            true
        }

    }

    private fun saveData() {
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        Log.d("StepsFragment", "$savedNumber")
        previousTotalSteps = savedNumber
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
