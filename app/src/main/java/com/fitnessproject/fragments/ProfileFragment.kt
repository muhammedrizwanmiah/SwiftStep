package com.fitnessproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitnessproject.LoginActivity

import com.fitnessproject.R
import com.fitnessproject.UpdateDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()
    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        emailText.text = user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //will wait in the event that name has changed in the database, waits for update method to prevent NullPointerException
                if (fullnameText == null){
                    return
                }

                fullnameText.text = snapshot.child("firstname").value.toString() + " " + snapshot.child("lastname").value.toString()
                genderText.text = "Gender: " + snapshot.child("gender").value.toString()
                ageText.text = "Age: " + snapshot.child("age").value.toString() + " years old"
                heightText.text = "Height: " + snapshot.child("height").value.toString() + "cm"
                weightText.text = "Weight: " + snapshot.child("weight").value.toString() + "kg"
                activityLevelText.text = "Activity Level: " + snapshot.child("activitylevel").value.toString()

                //logic for setting profile pic depending on user gender
                if (snapshot.child("gender").value.toString() == "Male"){
                    profilePic.setBackgroundResource(R.drawable.ic_profilepic_male) //if gender male, set pic to male

                } else {
                    profilePic.setBackgroundResource(R.drawable.ic_profilepic_female) //else set pic to female
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        logoutButton.setOnClickListener {
            auth.signOut()
            requireActivity().finish()
            val intent = Intent (requireActivity(), LoginActivity::class.java)
            startActivity(intent)

        }

        userDetailsFrameLayout.setOnClickListener {
            requireActivity()
            val intent = Intent (requireActivity(), UpdateDetails::class.java)
            startActivity(intent)
        }

    }
}
