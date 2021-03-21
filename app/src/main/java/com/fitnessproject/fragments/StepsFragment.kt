package com.fitnessproject.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
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

    val sdf = SimpleDateFormat("E")
    var currentDay = sdf.format(Date())

    //days counter
    var totalStepsMon = 0
    var countMon = 0

    var totalStepsTue = 0
    var countTue = 0

    var totalStepsWed = 0
    var countWed = 0

    var totalStepsThu = 0
    var countThu = 0

    var totalStepsFri = 0
    var countFri = 0

    var totalStepsSat = 0
    var countSat = 0

    var totalStepsSun = 0
    var countSun = 0

    var totalDays = 0 //used to calculate average daily steps, daily calories

    var totalCalMon = 0
    var totalCalTue = 0
    var totalCalWed = 0
    var totalCalThu = 0
    var totalCalFri = 0
    var totalCalSat = 0
    var totalCalSun = 0

    //total steps all time
    var totalStepsAllTime = 0

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

        tvTime.text = "Day: " + "$currentDay"
        tvStepsTaken.text = currentStepsInt.toString()

        circularProgressBar.apply{
            setProgressWithAnimation(currentStepsInt.toFloat(), 1500)
        }

        loadData()
        resetSteps()
        loadPage()
    }

    private fun saveToDay() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(currentDay == "Mon"){
                    userreference.child("step_data/Sun").setValue(currentStepsInt)
                    totalStepsSun += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countSun += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Sun").setValue(totalStepsSun)
                        userreference.child("step_data/total_days").setValue(totalDays) //increases total day by 1
                        userreference.child("step_data/count/count_for_Sun").setValue(countSun)
                    },500)
                }
                if(currentDay == "Tue"){
                    userreference.child("step_data/Mon").setValue(currentStepsInt)
                    totalStepsMon += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countMon += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Mon").setValue(totalStepsMon)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Mon").setValue(countMon)
                    },500)
                }
                if(currentDay == "Wed"){
                    userreference.child("step_data/Tue").setValue(currentStepsInt)
                    totalStepsTue += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countTue += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Tue").setValue(totalStepsTue)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Tue").setValue(countTue)
                    },500)
                }
                if(currentDay == "Thu"){
                    userreference.child("step_data/Wed").setValue(currentStepsInt)
                    totalStepsWed += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countWed += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Wed").setValue(totalStepsFri)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Wed").setValue(countWed)
                    },500)
                }
                if(currentDay == "Fri"){
                    userreference.child("step_data/Thu").setValue(currentStepsInt)
                    totalStepsThu += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countThu += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Thu").setValue(totalStepsFri)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Thu").setValue(countThu)
                    },500)

                }
                if(currentDay == "Sat"){
                    userreference.child("step_data/Fri").setValue(currentStepsInt)
                    totalStepsFri += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countFri += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Fri").setValue(totalStepsFri)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Fri").setValue(countFri)
                    },500)

                }
                if(currentDay == "Sun"){
                    userreference.child("step_data/Sat").setValue(currentStepsInt)
                    totalStepsSat += currentStepsInt
                    totalStepsAllTime += currentStepsInt
                    totalDays += 1
                    countSat += 1

                    Handler().postDelayed({
                        userreference.child("step_data/day_counter/Sat").setValue(totalStepsSat)
                        userreference.child("step_data/total_days").setValue(totalDays)
                        userreference.child("step_data/count/count_for_Sat").setValue(countSat)
                    },500)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Handler().postDelayed({
            resetStepsAuto()
        },500) //wait half a second before running method
    }

    private fun loadPage() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(tvStepGoal == null || tvStepsTaken == null || tvDistance == null || tvTime == null || tvCalories == null){
                    return
                }

                tvTime.text = "Day: " + "$currentDay"

                //calc stride length
                val heightInCM = snapshot.child("height").value.toString().toDouble()
                val strideLength = heightInCM * 0.43
                val stepsInCM = (strideLength * tvStepsTaken.text.toString().toDouble()) / 100000
                val distance = Math.round(stepsInCM * 100.0) / 100.0

                val weightInKG = snapshot.child("weight").value.toString().toDouble()
                val caloriesBurnedPerMile = 0.57 * (weightInKG * 2.2);
                val stepCountMile = 160934.4 / strideLength;
                val conversionFactor = caloriesBurnedPerMile / stepCountMile;
                val caloriesBurned = tvStepsTaken.text.toString().toDouble() * conversionFactor;
                val caloriesBurnedRounded = Math.round(caloriesBurned * 100.0) / 100.0

                //text for elements on the screen
                tvDistance.text = "Distance: " + distance.toString() + " km"
                tvCalories.text = "Calories burnt: " + caloriesBurnedRounded.toString() + " cal"
                tvStepGoal.text = "/" + snapshot.child("stepgoal").value.toString()

                yourHeightText.text = snapshot.child("height").value.toString() + " CM"
                yourWeightText.text = snapshot.child("weight").value.toString() + " KG"
                yourBMItext.text = snapshot.child("BMI").value.toString()

                circularProgressBar.progressMax = snapshot.child("stepgoal").value.toString().toFloat()

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

        val sdf1 = SimpleDateFormat("E")
        var currentDay1 = sdf1.format(Date())

        if(currentDay != currentDay1){ //checks if day has changed, calls saveToDay to save value
            currentDay = currentDay1
            saveToDay()
        }

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(tvStepGoal == null || tvStepsTaken == null || tvDistance == null || tvTime == null || tvCalories == null){
                    return
                }
                //calc stride length
                val heightInCM = snapshot.child("height").value.toString().toDouble()
                val strideLength = heightInCM * 0.43
                val stepsInCM = (strideLength * tvStepsTaken.text.toString().toDouble()) / 100000
                val distance = Math.round(stepsInCM * 100.0) / 100.0

                val weightInKG = snapshot.child("weight").value.toString().toDouble()
                val caloriesBurnedPerMile = 0.57 * (weightInKG * 2.2);
                val stepCountMile = 160934.4 / strideLength;
                val conversionFactor = caloriesBurnedPerMile / stepCountMile;
                val caloriesBurned = tvStepsTaken.text.toString().toDouble() * conversionFactor;
                val caloriesBurnedRounded = Math.round(caloriesBurned * 100.0) / 100.0

                tvCalories.text = "Calories burnt: " + caloriesBurnedRounded.toString() + " cal"
                tvDistance.text = "Distance: " + distance.toString() + " km"
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

    private fun resetSteps() {
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

    private fun resetStepsAuto() {

            previousTotalSteps = totalSteps
            tvStepsTaken.text = 0.toString()
            currentStepsInt = 0
            circularProgressBar.apply{
                setProgressWithAnimation(0f, 1500)
            }
            saveData()
            true
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
