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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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

    var executed = false

    //sensor code

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private var currentStepsInt = 0
    private var currentCalories = 0.0
    private var currentDistance = 0.0

    var distanceRan = 0.0

    val sdf = SimpleDateFormat("E")
    var currentDay = sdf.format(Date())

    var stepsForMon = 0

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

                if(!executed && currentDay == "Mon"){

                    executed = true

                    userreference.child("user_data/most_recent_per_day/Sun").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Sun").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Sun").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Sun").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Sun").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }
                if(!executed && currentDay == "Tue"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Mon").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Mon").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Mon").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Mon").setValue(ServerValue.increment( currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(+ currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Mon").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }
                if(!executed && currentDay == "Wed"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Tue").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Tue").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Tue").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Tue").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Tue").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }
                if(!executed && currentDay == "Thu"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Wed").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Wed").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Wed").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Wed").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(+ currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Wed").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }
                if(!executed && currentDay == "Fri"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Thu").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Thu").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Thu").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Thu").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(+ currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Thu").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }

                if(!executed && currentDay == "Sat"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Fri").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Fri").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Fri").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Fri").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(currentStepsInt) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Fri").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }

                if(!executed && currentDay == "Sun"){
                    executed = true
                    userreference.child("user_data/most_recent_per_day/Sat").setValue(currentStepsInt) //saves the stats for last saved day
                    userreference.child("user_data/total_steps_by_day/Sat").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for Sat
                    userreference.child("user_data/total_calories_by_day/Sat").setValue(ServerValue.increment(currentCalories)) //adds to total calories for Sat
                    userreference.child("user_data/total_distance_by_day/Sat").setValue(ServerValue.increment(currentDistance)) //adds to total distance for Sat

                    //all time stats for steps, calorie, distance
                    userreference.child("user_data/all_time/steps").setValue(ServerValue.increment(currentStepsInt.toString().toDouble())) //adds to total steps for day
                    userreference.child("user_data/all_time/calories").setValue(ServerValue.increment(currentCalories)) //adds to total calories for day
                    userreference.child("user_data/all_time/distance").setValue(ServerValue.increment(currentDistance))

                    userreference.child("user_data/counter_for_day/Sat").setValue(ServerValue.increment(1)) //counter for Sat
                    userreference.child("user_data/all_time_day_counter").setValue(ServerValue.increment(1)) //add to all time counter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        executed = false
        Handler().postDelayed({
            resetStepsAuto()
        },500) //wait half a second before running method
    }

    private fun loadPage() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.child("user_data/total_calories_by_day/Sun")?.setValue(7.93f)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(tvStepGoal == null || tvStepsTaken == null || tvDistance == null || tvTime == null || tvCalories == null){
                    return
                }

                tvTime.text = "Day: " + "$currentDay"

                //calc distance
                val heightInCM = snapshot.child("height").value.toString().toDouble()
                val strideLength = heightInCM * 0.43
                val stepsInCM = (strideLength * tvStepsTaken.text.toString().toDouble()) / 100000
                val distance = Math.round(stepsInCM * 100.0) / 100.0

                //calc calories
                val weightInKG = snapshot.child("weight").value.toString().toDouble()
                val caloriesBurnedPerMile = 0.57 * (weightInKG * 2.2);
                val stepCountMile = 160934.4 / strideLength;
                val conversionFactor = caloriesBurnedPerMile / stepCountMile;
                val caloriesBurned = tvStepsTaken.text.toString().toDouble() * conversionFactor;
                val caloriesBurnedRounded = Math.round(caloriesBurned * 100.0) / 100.0

                //**************STATS***********
                // calculate stats
                // most recent steps

                /*
                tvMostRecentMon.text = snapshot.child("user_data/most_recent_per_day/Mon").value.toString()
                tvMostRecentTue.text = snapshot.child("user_data/most_recent_per_day/Tue").value.toString()
                tvMostRecentWed.text = snapshot.child("user_data/most_recent_per_day/Wed").value.toString()
                tvMostRecentThu.text = snapshot.child("user_data/most_recent_per_day/Thu").value.toString()
                tvMostRecentFri.text = snapshot.child("user_data/most_recent_per_day/Fri").value.toString()
                tvMostRecentSat.text = snapshot.child("user_data/most_recent_per_day/Sat").value.toString()
                tvMostRecentSun.text = snapshot.child("user_data/most_recent_per_day/Sun").value.toString()
                 */

                //steps by day

                //initalise var for steps by day
                var stepsByMon = 0
                var stepsByTue = 0
                var stepsByWed = 0
                var stepsByThu = 0
                var stepsByFri = 0
                var stepsBySat = 0
                var stepsBySun = 0

                //init cal
                var calByMon = 0.0
                var calByTue = 0.0
                var calByWed = 0.0
                var calByThu = 0.0
                var calByFri = 0.0
                var calBySat = 0.0
                var calBySun = 0.0

                //init distance
                var distByMon = 0.0
                var distByTue = 0.0
                var distByWed = 0.0
                var distByThu = 0.0
                var distByFri = 0.0
                var distBySat = 0.0
                var distBySun = 0.0

                //steps

                stepsByMon = if (snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Mon").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsByTue = if (snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Tue").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Tue").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsByWed = if (snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Wed").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsByThu = if (snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Thu").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsByFri = if (snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Fri").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsBySat = if (snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Sat").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                } else {
                    0
                }

                stepsBySun = if (snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_steps_by_day/Sun").value.toString()
                        .toInt() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                } else {
                    0
                }



                //calories by day

                calByMon = if (snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Mon").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                calByTue = if (snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Tue").value.toString().toDouble() / snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt()
                } else {
                    0.0
                }

                calByWed = if (snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Wed").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                calByThu = if (snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Thu").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                calByFri = if (snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Fri").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                calBySat = if (snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Sat").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                calBySun = if (snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_calories_by_day/Sun").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                } else {
                    0.0
                }
                //distance by day
                distByMon = if (snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Mon").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distByTue = if (snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Tue").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Tue").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distByWed = if (snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Wed").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distByThu = if (snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Thu").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distByFri = if (snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Fri").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distBySat = if (snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Sat").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                distBySun = if (snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() !=0) {
                    snapshot.child("user_data/total_distance_by_day/Sun").value.toString()
                        .toDouble() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                } else {
                    0.0
                }

                //all time stats
                //all time steps, cal, dist
                var allTimeSteps = snapshot.child("user_data/all_time/steps").value.toString()
                var allTimeCal = snapshot.child("user_data/all_time/calories").value.toString()
                var allTimeDist = snapshot.child("user_data/all_time/distance").value.toString()

                //**************************************************

                //text for elements on the screen
                tvDistance.text = distance.toString()
                tvCalories.text = caloriesBurnedRounded.toString()
                tvStepGoal.text = "/" + snapshot.child("stepgoal").value.toString()

                yourHeightText.text = snapshot.child("height").value.toString() + " CM"
                yourWeightText.text = snapshot.child("weight").value.toString() + " KG"
                yourBMItext.text = snapshot.child("BMI").value.toString()

                circularProgressBar.progressMax = snapshot.child("stepgoal").value.toString().toFloat()

                mondayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                mondayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Mon").value.toString().toFloat(), 1500)
                }

                var monPercentage = snapshot.child("user_data/most_recent_per_day/Tue").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                monCheck.visibility = (View.GONE)

                if (monPercentage in 0.0..25.0){
                    mondayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (monPercentage in 26.0..50.0){
                    mondayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (monPercentage in 51.0..75.0){
                    mondayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (monPercentage in 76.0..99.9){
                    mondayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (monPercentage >= 100.0){
                    mondayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    monCheck.visibility = (View.VISIBLE)
                }

                tuesdayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                tuesdayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Tue").value.toString().toFloat(), 1500)
                }

                var tuePercentage = snapshot.child("user_data/most_recent_per_day/Tue").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                tueCheck.visibility = (View.GONE)

                if (tuePercentage in 0.0..25.0){
                    tuesdayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (tuePercentage in 26.0..50.0){
                    tuesdayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (tuePercentage in 51.0..75.0){
                    tuesdayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (tuePercentage in 76.0..99.9){
                    tuesdayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (tuePercentage >= 100.0){
                    tuesdayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    tueCheck.visibility = (View.VISIBLE)
                }

                wednesdayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                wednesdayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Wed").value.toString().toFloat(), 1500)
                }

                var wedPercentage = snapshot.child("user_data/most_recent_per_day/Wed").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                wedCheck.visibility = (View.GONE)

                if (wedPercentage in 0.0..25.0){
                    wednesdayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (wedPercentage in 26.0..50.0){
                    wednesdayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (wedPercentage in 51.0..75.0){
                    wednesdayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (wedPercentage in 76.0..99.9){
                    wednesdayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (wedPercentage >= 100.0){
                    wednesdayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    wedCheck.visibility = (View.VISIBLE)
                }

                thursdayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                thursdayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Thu").value.toString().toFloat(), 1500)
                }

                var thuPercentage = snapshot.child("user_data/most_recent_per_day/Thu").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                thuCheck.visibility = (View.GONE)

                if (thuPercentage in 0.0..25.0){
                    thursdayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (thuPercentage in 26.0..50.0){
                    thursdayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (thuPercentage in 51.0..75.0){
                    thursdayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (thuPercentage in 76.0..99.9){
                    thursdayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (thuPercentage >= 100.0){
                    thursdayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    thuCheck.visibility = (View.VISIBLE)
                }

                fridayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                fridayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Sat").value.toString().toFloat(), 1500)
                }

                var friPercentage = snapshot.child("user_data/most_recent_per_day/Sat").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                friCheck.visibility = (View.GONE)

                if (friPercentage in 0.0..25.0){
                    fridayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (friPercentage in 26.0..50.0){
                    fridayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (friPercentage in 51.0..75.0){
                    fridayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (friPercentage in 76.0..99.9){
                    fridayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (friPercentage >= 100.0){
                    fridayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    friCheck.visibility = (View.VISIBLE)
                }

                saturdayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                saturdayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Sat").value.toString().toFloat(), 1500)
                }

                var satPercentage = snapshot.child("user_data/most_recent_per_day/Sat").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                satCheck.visibility = (View.GONE)

                if (satPercentage in 0.0..25.0){
                    saturdayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (satPercentage in 26.0..50.0){
                    saturdayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (satPercentage in 51.0..75.0){
                    saturdayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (satPercentage in 76.0..99.9){
                    saturdayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (satPercentage  >= 100.0){
                    saturdayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    satCheck.visibility = (View.VISIBLE)
                }

                sundayProgress.progressMax = snapshot.child("stepgoal").value.toString().toFloat()
                sundayProgress.apply{
                    setProgressWithAnimation(snapshot.child("user_data/most_recent_per_day/Sun").value.toString().toFloat(), 1500)
                }

                var sunPercentage = snapshot.child("user_data/most_recent_per_day/Sun").value.toString().toDouble() / snapshot.child("stepgoal").value.toString().toDouble() * 100
                sunCheck.visibility = (View.GONE)

                if (sunPercentage in 0.0..25.0){
                    sundayProgress.progressBarColor = Color.parseColor("#ef5350") //set to red
                } else if (sunPercentage in 26.0..50.0){
                    sundayProgress.progressBarColor = Color.parseColor("#ffb300") //set to orange
                } else if (sunPercentage in 51.0..75.0){
                    sundayProgress.progressBarColor = Color.parseColor("#ffee58") //set to yellow
                } else if (sunPercentage in 76.0..99.9){
                    sundayProgress.progressBarColor = Color.parseColor("#66bb6a") //set to green
                } else if (sunPercentage >= 100.0){
                    sundayProgress.progressBarColor = Color.parseColor("#69f0ae") //set to green
                    sunCheck.visibility = (View.VISIBLE)
                }




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
                //calculate distance based on height and stride length
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

                tvCalories.text = caloriesBurnedRounded.toString()
                tvDistance.text = distance.toString()

                currentCalories = caloriesBurnedRounded
                currentDistance = distance
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
            currentDistance = 0.0 //may not need
            currentCalories =0.0 //may not need

            circularProgressBar.apply{
                setProgressWithAnimation(0f, 1500)
            }
            saveData()
            true
        }

    }

    private fun resetStepsAuto() {

        if(tvStepGoal == null || tvStepsTaken == null || tvDistance == null || tvTime == null || tvCalories == null){

            previousTotalSteps = totalSteps
            tvStepsTaken.text = 0.toString()
            currentStepsInt = 0
            circularProgressBar.apply{
                setProgressWithAnimation(0f, 1500)
            }
            saveData()
            true

            return
        } else {

            previousTotalSteps = totalSteps
            tvStepsTaken.text = 0.toString()
            currentStepsInt = 0
            circularProgressBar.apply {
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
