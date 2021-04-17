package com.fitnessproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_distance_badges.*
import kotlin.math.roundToInt

class DistanceBadges : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance_badges)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        distanceBadgesLoadPage()
    }

    private fun distanceBadgesLoadPage() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        currentUSerDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val allTimeDist1 = snapshot.child("user_data/total_distance_by_day/Mon").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Tue").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Wed").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Thu").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Fri").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Sat").value.toString().toDouble() +
                        snapshot.child("user_data/total_distance_by_day/Sun").value.toString().toDouble()

                val allTimeDist = allTimeDist1.roundToInt()

                val percentage5Distance = (allTimeDist / 5.00 * 100).roundToInt()
                distance5ProgressText.text = "You are $percentage5Distance% of the way there!"
                distance5Progress.progress = allTimeDist

                if(percentage5Distance >= 100){
                    distance5.setImageResource(R.drawable.distance5)
                    distance5Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance5InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance5ProgressLayout.visibility = (View.INVISIBLE)
                    distance5ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance5.setImageResource(R.drawable.distance5u)
                    distance5Layout.setBackgroundResource(R.drawable.card_body)
                    distance5InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance5ProgressLayout.visibility = (View.VISIBLE)
                    distance5ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                /////////

                val percentage10Distance = (allTimeDist / 10.00 * 100).roundToInt()
                distance10ProgressText.text = "You are $percentage10Distance% of the way there!"
                distance10Progress.progress = allTimeDist.toString().toInt()

                if(percentage10Distance >= 100){
                    distance10.setImageResource(R.drawable.distance10)
                    distance10Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance10InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance10ProgressLayout.visibility = (View.INVISIBLE)
                    distance10ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance10.setImageResource(R.drawable.distance10u)
                    distance10Layout.setBackgroundResource(R.drawable.card_body)
                    distance10InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance10ProgressLayout.visibility = (View.VISIBLE)
                    distance10ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ///////////

                val percentage20Distance = (allTimeDist / 20.00 * 100).roundToInt()
                distance20ProgressText.text = "You are $percentage20Distance% of the way there!"
                distance20Progress.progress = allTimeDist.toString().toInt()

                if(percentage20Distance >= 100){
                    distance20.setImageResource(R.drawable.distance20)
                    distance20Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance20InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance20ProgressLayout.visibility = (View.INVISIBLE)
                    distance20ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance20.setImageResource(R.drawable.distance20u)
                    distance20Layout.setBackgroundResource(R.drawable.card_body)
                    distance20InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance20ProgressLayout.visibility = (View.VISIBLE)
                    distance20ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ///////////

                val percentage30Distance = (allTimeDist / 30.00 * 100).roundToInt()
                distance30ProgressText.text = "You are $percentage30Distance% of the way there!"
                distance30Progress.progress = allTimeDist.toString().toInt()

                if(percentage30Distance >= 100){
                    distance30.setImageResource(R.drawable.distance30)
                    distance30Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance30InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance30ProgressLayout.visibility = (View.INVISIBLE)
                    distance30ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance30.setImageResource(R.drawable.distance30u)
                    distance30Layout.setBackgroundResource(R.drawable.card_body)
                    distance30InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance30ProgressLayout.visibility = (View.VISIBLE)
                    distance30ProgressCheckLayout.visibility = (View.INVISIBLE)
                }
                /////////////

                val percentage42Distance = (allTimeDist / 42.00 * 100).roundToInt()
                distance42ProgressText.text = "You are $percentage42Distance% of the way there!"
                distance42Progress.progress = allTimeDist.toString().toInt()

                if(percentage42Distance >= 100){
                    distance42.setImageResource(R.drawable.distance42)
                    distance42Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance42InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance42ProgressLayout.visibility = (View.INVISIBLE)
                    distance42ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance42.setImageResource(R.drawable.distance42u)
                    distance42Layout.setBackgroundResource(R.drawable.card_body)
                    distance42InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance42ProgressLayout.visibility = (View.VISIBLE)
                    distance42ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

                ////////////

                val percentage60Distance = (allTimeDist / 60.00 * 100).roundToInt()
                distance60ProgressText.text = "You are $percentage60Distance% of the way there!"
                distance60Progress.progress = allTimeDist.toString().toInt()

                if(percentage60Distance >= 100){
                    distance60.setImageResource(R.drawable.distance60)
                    distance60Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    distance60InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    distance60ProgressLayout.visibility = (View.INVISIBLE)
                    distance60ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    distance60.setImageResource(R.drawable.distance60u)
                    distance60Layout.setBackgroundResource(R.drawable.card_body)
                    distance60InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    distance60ProgressLayout.visibility = (View.VISIBLE)
                    distance60ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
