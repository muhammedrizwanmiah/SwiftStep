package com.fitnessproject.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fitnessproject.R
import com.fitnessproject.bmiCalculator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_steps.*

/**
 * A simple [Fragment] subclass.
 */
class StepsFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

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

        loadPage()
    }

    private fun loadPage() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

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

    }

}
