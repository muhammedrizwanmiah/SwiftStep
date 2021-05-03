package com.fitnessproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitnessproject.*
import com.fitnessproject.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_achievements.*

class AchievementsFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
        ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        achievementsLoadPage()
    }

    private fun achievementsLoadPage() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val allTimeSteps = snapshot.child("user_data/total_steps_by_day/Mon").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Tue").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Wed").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Thu").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Fri").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Sat").value.toString().toInt() +
                        snapshot.child("user_data/total_steps_by_day/Sun").value.toString().toInt()

                val allTimeCal = snapshot.child("user_data/total_calories_by_day/Mon").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Tue").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Wed").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Thu").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Fri").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Sat").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Sun").value.toString().toDouble()

                val allTimeDist = snapshot.child("user_data/total_distance_by_day/Mon").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Tue").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Wed").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Thu").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Fri").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Sat").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Sun").value.toString().toDouble()

                val totalDay = snapshot.child("user_data/all_time_day_counter").value.toString().toInt()

                if(steps3kimg == null){
                    return
                }

                if(allTimeSteps >= 3000){
                    steps3kimg.setImageResource(R.drawable.steps3k)
                } else {
                    steps3kimg.setImageResource(R.drawable.steps3ku)
                }
                if(allTimeSteps >= 10000){
                    steps10kimg.setImageResource(R.drawable.steps10k)
                } else {
                    steps10kimg.setImageResource(R.drawable.steps10ku)
                }
                if(allTimeSteps >= 20000){
                    steps20kimg.setImageResource(R.drawable.steps20k)
                } else {
                    steps20kimg.setImageResource(R.drawable.steps20ku)
                }

                if(allTimeCal >= 100){
                    cal100.setImageResource(R.drawable.calories100)
                } else {
                    cal100.setImageResource(R.drawable.calories100u)
                }
                if(allTimeCal >= 300){
                    cal300.setImageResource(R.drawable.calories300)
                } else {
                    cal300.setImageResource(R.drawable.calories300u)
                }
                if(allTimeCal >= 500){
                    cal500.setImageResource(R.drawable.calories500)
                } else {
                    cal500.setImageResource(R.drawable.calories500u)
                }

                if(totalDay >= 3){
                    days3.setImageResource(R.drawable.days3)
                } else {
                    days3.setImageResource(R.drawable.days3u)
                }
                if(totalDay >= 7){
                    days7.setImageResource(R.drawable.days7)
                } else {
                    days7.setImageResource(R.drawable.days7u)
                }
                if(totalDay >= 14){
                    days14.setImageResource(R.drawable.days14)
                } else {
                    days14.setImageResource(R.drawable.days14u)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        AStepsHeadingBG.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), StepsBadges::class.java)
            startActivity(intent)
        }

        ACaloriesHeadingBG.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), CaloriesBadges::class.java)
            startActivity(intent)
        }

        ADistanceHeadingBG.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), DistanceBadges::class.java)
            startActivity(intent)
        }

        ADaysHeadingBG.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), DaysBadges::class.java)
            startActivity(intent)
        }

    }

}

