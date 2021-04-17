package com.fitnessproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_steps_badges.*
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class StepsBadges : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps_badges)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        stepsBadgesLoadPage()
    }

    private fun stepsBadgesLoadPage() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        currentUSerDb?.addValueEventListener(object : ValueEventListener {
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

                val totalDays = snapshot.child("user_data/all_time_day_counter").value.toString().toInt()

                val percentage3kSteps = (allTimeSteps / 3000.00 * 100).roundToInt()

                steps3kProgressText.text = "You are $percentage3kSteps% of the way there!"
                steps3kProgress.progress = allTimeSteps.toString().toInt()
                if(percentage3kSteps >= 100){
                    steps3k.setImageResource(R.drawable.steps3k)
                    steps3kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps3kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps3kProgressLayout.visibility = (View.INVISIBLE)
                    steps3kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps3k.setImageResource(R.drawable.steps3ku)
                    steps3kLayout.setBackgroundResource(R.drawable.card_body)
                    steps3kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps3kProgressLayout.visibility = (View.VISIBLE)
                    steps3kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ////////////////////

                val percentage10kSteps = (allTimeSteps / 10000.00 * 100).roundToInt()
                steps10kProgressText.text = "You are $percentage10kSteps% of the way there!"
                steps10kProgress.progress = allTimeSteps
                if(percentage10kSteps >= 100){
                    steps10k.setImageResource(R.drawable.steps10k)
                    steps10kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps10kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps10kProgressLayout.visibility = (View.INVISIBLE)
                    steps10kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps10k.setImageResource(R.drawable.steps10ku)
                    steps10kLayout.setBackgroundResource(R.drawable.card_body)
                    steps10kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps10kProgressLayout.visibility = (View.VISIBLE)
                    steps10kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ////////////////////

                val percentage20kSteps = (allTimeSteps / 20000.00 * 100).roundToInt()
                steps20kProgressText.text = "You are $percentage20kSteps% of the way there!"
                steps20kProgress.progress = allTimeSteps
                if(percentage20kSteps >= 100){
                    steps20k.setImageResource(R.drawable.steps20k)
                    steps20kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps20kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps20kProgressLayout.visibility = (View.INVISIBLE)
                    steps20kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps20k.setImageResource(R.drawable.steps20ku)
                    steps20kLayout.setBackgroundResource(R.drawable.card_body)
                    steps20kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps20kProgressLayout.visibility = (View.VISIBLE)
                    steps20kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                //////////////////////

                val percentage30kSteps = (allTimeSteps / 30000.00 * 100).roundToInt()
                steps30kProgressText.text = "You are $percentage30kSteps% of the way there!"
                steps30kProgress.progress = allTimeSteps
                if(percentage30kSteps >= 100){
                    steps30k.setImageResource(R.drawable.steps30k)
                    steps30kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps30kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps30kProgressLayout.visibility = (View.INVISIBLE)
                    steps30kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps30k.setImageResource(R.drawable.steps30ku)
                    steps30kLayout.setBackgroundResource(R.drawable.card_body)
                    steps30kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps30kProgressLayout.visibility = (View.VISIBLE)
                    steps30kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                /////////////////////

                val percentage40kSteps = (allTimeSteps / 40000.00 * 100).roundToInt()
                steps40kProgressText.text = "You are $percentage40kSteps% of the way there!"
                steps40kProgress.progress = allTimeSteps

                if(percentage40kSteps >= 100){
                    steps40k.setImageResource(R.drawable.steps40k)
                    steps40kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps40kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps40kProgressLayout.visibility = (View.INVISIBLE)
                    steps40kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps40k.setImageResource(R.drawable.steps40ku)
                    steps40kLayout.setBackgroundResource(R.drawable.card_body)
                    steps40kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps40kProgressLayout.visibility = (View.VISIBLE)
                    steps40kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ////////////////////////

                val percentage60kSteps = (allTimeSteps / 60000.00 * 100).roundToInt()
                steps60kProgressText.text = "You are $percentage60kSteps% of the way there!"
                steps60kProgress.progress = allTimeSteps
                if(percentage60kSteps >= 100){
                    steps60k.setImageResource(R.drawable.steps60k)
                    steps60kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
                    steps60kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    steps60kProgressLayout.visibility = (View.INVISIBLE)
                    steps60kProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    steps60k.setImageResource(R.drawable.steps60ku)
                    steps60kLayout.setBackgroundResource(R.drawable.card_body)
                    steps60kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    steps60kProgressLayout.visibility = (View.VISIBLE)
                    steps60kProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                /////////////////////////

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
