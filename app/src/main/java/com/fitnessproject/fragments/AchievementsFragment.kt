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

