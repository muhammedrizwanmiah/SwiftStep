package com.fitnessproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitnessproject.LoginActivity

import com.fitnessproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration.*
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

        emailText.text = "Email  -- > " + user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                firstnameText.text = "Firstname - - > " + snapshot.child("firstname").value.toString()
                lastnameText.text = "Last name - -> " + snapshot.child("lastname").value.toString()
                genderText.text = "Gender - ->" + snapshot.child("gender").value.toString()
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
    }
}
