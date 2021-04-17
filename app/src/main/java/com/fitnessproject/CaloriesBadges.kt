package com.fitnessproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_calories_badges.*
import kotlin.math.roundToInt

class CaloriesBadges : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories_badges)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        caloriesBadgesLoadPage()
    }

    private fun caloriesBadgesLoadPage() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        currentUSerDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val allTimeCal = snapshot.child("user_data/total_calories_by_day/Mon").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Tue").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Wed").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Thu").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Fri").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Sat").value.toString().toDouble() +
                        snapshot.child("user_data/total_calories_by_day/Sun").value.toString().toDouble()

                val percentage100Calories = (allTimeCal / 100.00 * 100).roundToInt()
                calories100ProgressText.text = "You are $percentage100Calories% of the way there!"
                calories100Progress.progress = allTimeCal.toInt()
                if(percentage100Calories >= 100){
                    calories100.setImageResource(R.drawable.calories100)
                    calories100Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories100InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories100ProgressLayout.visibility = (View.INVISIBLE)
                    calories100ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories100.setImageResource(R.drawable.calories100u)
                    calories100Layout.setBackgroundResource(R.drawable.card_body)
                    calories100InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories100ProgressLayout.visibility = (View.VISIBLE)
                    calories100ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ////////////

                val percentage300Calories = (allTimeCal / 300.00 * 100).roundToInt()
                calories300ProgressText.text = "You are $percentage300Calories% of the way there!"
                calories300Progress.progress = allTimeCal.toInt()

                if(percentage300Calories >= 100){
                    calories300.setImageResource(R.drawable.calories300)
                    calories300Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories300InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories300ProgressLayout.visibility = (View.INVISIBLE)
                    calories300ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories300.setImageResource(R.drawable.calories300u)
                    calories300Layout.setBackgroundResource(R.drawable.card_body)
                    calories300InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories300ProgressLayout.visibility = (View.VISIBLE)
                    calories300ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                //////////////////////

                val percentage500Calories = (allTimeCal / 500.00 * 100).roundToInt()
                calories500ProgressText.text = "You are $percentage500Calories% of the way there!"
                calories500Progress.progress = allTimeCal.toInt()

                if(percentage500Calories >= 100){
                    calories500.setImageResource(R.drawable.calories500)
                    calories500Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories500InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories500ProgressLayout.visibility = (View.INVISIBLE)
                    calories500ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories500.setImageResource(R.drawable.calories500u)
                    calories500Layout.setBackgroundResource(R.drawable.card_body)
                    calories500InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories500ProgressLayout.visibility = (View.VISIBLE)
                    calories500ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                //////////////////////

                val percentage1000Calories = (allTimeCal / 1000.00 * 100).roundToInt()
                calories1000ProgressText.text = "You are $percentage1000Calories% of the way there!"
                calories1000Progress.progress = allTimeCal.toInt()

                if(percentage1000Calories >= 100){
                    calories1000.setImageResource(R.drawable.calories1000)
                    calories1000Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories1000InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories1000ProgressLayout.visibility = (View.INVISIBLE)
                    calories1000ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories1000.setImageResource(R.drawable.calories1000u)
                    calories1000Layout.setBackgroundResource(R.drawable.card_body)
                    calories1000InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories1000ProgressLayout.visibility = (View.VISIBLE)
                    calories1000ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                //////////////////////

                val percentage1500Calories = (allTimeCal / 1500.00 * 100).roundToInt()
                calories1500ProgressText.text = "You are $percentage1500Calories% of the way there!"
                calories1500Progress.progress = allTimeCal.toInt()

                if(percentage1500Calories >= 100){
                    calories1500.setImageResource(R.drawable.calories1500)
                    calories1500Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories1500InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories1500ProgressLayout.visibility = (View.INVISIBLE)
                    calories1500ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories1500.setImageResource(R.drawable.calories1500u)
                    calories1500Layout.setBackgroundResource(R.drawable.card_body)
                    calories1500InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories1500ProgressLayout.visibility = (View.VISIBLE)
                    calories1500ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                //////////////////////

                val percentage3000Calories = (allTimeCal / 3000.00 * 100).roundToInt()
                calories3000ProgressText.text = "You are $percentage3000Calories% of the way there!"
                calories3000Progress.progress = allTimeCal.toInt()

                if(percentage3000Calories >= 100){
                    calories3000.setImageResource(R.drawable.calories3000)
                    calories3000Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    calories3000InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    calories3000ProgressLayout.visibility = (View.INVISIBLE)
                    calories3000ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    calories3000.setImageResource(R.drawable.calories3000u)
                    calories3000Layout.setBackgroundResource(R.drawable.card_body)
                    calories3000InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    calories3000ProgressLayout.visibility = (View.VISIBLE)
                    calories3000ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ///////////

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
