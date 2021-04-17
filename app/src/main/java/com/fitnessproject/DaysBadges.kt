package com.fitnessproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_days_badges.*
import kotlin.math.roundToInt

class DaysBadges : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days_badges)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        daysBadgesLoadPage()
    }

    private fun daysBadgesLoadPage() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        currentUSerDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val totalDays = snapshot.child("user_data/all_time_day_counter").value.toString().toInt()

                val percentage3Days = (totalDays / 3.00 * 100).roundToInt()
                days3ProgressText.text = "You are $percentage3Days% of the way there!"
                days3Progress.progress = totalDays.toString().toInt()

                if(percentage3Days >= 100){
                    days3.setImageResource(R.drawable.days3)
                    days3Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days3InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days3ProgressLayout.visibility = (View.INVISIBLE)
                    days3ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days3.setImageResource(R.drawable.days3u)
                    days3Layout.setBackgroundResource(R.drawable.card_body)
                    days3InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days3ProgressLayout.visibility = (View.VISIBLE)
                    days3ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

/////////

                val percentage7Days = (totalDays / 7.00 * 100).roundToInt()
                days7ProgressText.text = "You are $percentage7Days% of the way there!"
                days7Progress.progress = totalDays.toString().toInt()

                if(percentage7Days >= 100){
                    days7.setImageResource(R.drawable.days7)
                    days7Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days7InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days7ProgressLayout.visibility = (View.INVISIBLE)
                    days7ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days7.setImageResource(R.drawable.days7u)
                    days7Layout.setBackgroundResource(R.drawable.card_body)
                    days7InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days7ProgressLayout.visibility = (View.VISIBLE)
                    days7ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

///////////

                val percentage14Days = (totalDays / 14.00 * 100).roundToInt()
                days14ProgressText.text = "You are $percentage14Days% of the way there!"
                days14Progress.progress = totalDays.toString().toInt()

                if(percentage14Days >= 100){
                    days14.setImageResource(R.drawable.days14)
                    days14Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days14InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days14ProgressLayout.visibility = (View.INVISIBLE)
                    days14ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days14.setImageResource(R.drawable.days14u)
                    days14Layout.setBackgroundResource(R.drawable.card_body)
                    days14InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days14ProgressLayout.visibility = (View.VISIBLE)
                    days14ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

///////////

                val percentage30Days = (totalDays / 30.00 * 100).roundToInt()
                days30ProgressText.text = "You are $percentage30Days% of the way there!"
                days30Progress.progress = totalDays.toString().toInt()

                if(percentage30Days >= 100){
                    days30.setImageResource(R.drawable.days30)
                    days30Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days30InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days30ProgressLayout.visibility = (View.INVISIBLE)
                    days30ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days30.setImageResource(R.drawable.days30u)
                    days30Layout.setBackgroundResource(R.drawable.card_body)
                    days30InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days30ProgressLayout.visibility = (View.VISIBLE)
                    days30ProgressCheckLayout.visibility = (View.INVISIBLE)
                }
/////////////

                val percentage60Days = (totalDays / 42.00 * 100).roundToInt()
                days60ProgressText.text = "You are $percentage60Days% of the way there!"
                days60Progress.progress = totalDays.toString().toInt()

                if(percentage60Days >= 100){
                    days60.setImageResource(R.drawable.days60)
                    days60Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days60InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days60ProgressLayout.visibility = (View.INVISIBLE)
                    days60ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days60.setImageResource(R.drawable.days60u)
                    days60Layout.setBackgroundResource(R.drawable.card_body)
                    days60InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days60ProgressLayout.visibility = (View.VISIBLE)
                    days60ProgressCheckLayout.visibility = (View.INVISIBLE)
                }

////////////

                val percentage100Days = (totalDays / 60.00 * 100).roundToInt()
                days100ProgressText.text = "You are $percentage100Days% of the way there!"
                days100Progress.progress = totalDays.toString().toInt()

                if(percentage100Days >= 100){
                    days100.setImageResource(R.drawable.days100)
                    days100Layout.setBackgroundResource(R.drawable.card_achievement_checked)
                    days100InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    days100ProgressLayout.visibility = (View.INVISIBLE)
                    days100ProgressCheckLayout.visibility = (View.VISIBLE)
                } else {
                    days100.setImageResource(R.drawable.days100u)
                    days100Layout.setBackgroundResource(R.drawable.card_body)
                    days100InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
                    days100ProgressLayout.visibility = (View.VISIBLE)
                    days100ProgressCheckLayout.visibility = (View.INVISIBLE)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
