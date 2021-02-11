package com.fitnessproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register() {

        registerButton.setOnClickListener {

            if(TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.setError("Please enter first name.")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(lastnameInput.text.toString())) {
                lastnameInput.setError("Please enter last name.")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(usernameInput.text.toString())) {
                usernameInput.setError("Please enter e-mail.")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ageInput.text.toString())) {
                ageInput.setError("Please enter age.")
                return@setOnClickListener
            }
            else if(ageInput.text.toString().toInt() < 16) {
                ageInput.setError("Age cannot be less than 16.")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(heightInput.text.toString())) {
                heightInput.setError("Please enter height.")
                return@setOnClickListener
            }
            else if(heightInput.text.toString().toInt() < 130) {
                heightInput.setError("Minimum height accepted for this app is 130cm.")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(weightInput.text.toString())) {
                weightInput.setError("Please enter weight")
                return@setOnClickListener
            }
            else if(weightInput.text.toString().toInt() < 40) {
                weightInput.setError("Minimum weight accepted for this app is 40kg.")
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(usernameInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

                        val genderRadioButtonText = when (registerGenderGroup.checkedRadioButtonId) {
                            R.id.radioMaleBtn -> radioMaleBtn.text
                            else -> radioFemaleBtn.text
                        }

                        val activityLevelText = when (activityLevelGroup.checkedRadioButtonId){
                            R.id.radioLowActivity -> radioLowActivity.text
                            R.id.radioModerateActivity -> radioModerateActivity.text
                            R.id.radioHighActivity -> radioHighActivity.text
                            else -> null
                        }

                        currentUSerDb?.child("firstname")?.setValue(firstnameInput.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(lastnameInput.text.toString())
                        currentUSerDb?.child("gender")?.setValue(genderRadioButtonText.toString())
                        currentUSerDb?.child("age")?.setValue(ageInput.text.toString().toInt())
                        currentUSerDb?.child("height")?.setValue(heightInput.text.toString().toInt())
                        currentUSerDb?.child("weight")?.setValue(weightInput.text.toString().toInt())
                        currentUSerDb?.child("activitylevel")?.setValue(activityLevelText.toString())


                        Toast.makeText(this@RegistrationActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
