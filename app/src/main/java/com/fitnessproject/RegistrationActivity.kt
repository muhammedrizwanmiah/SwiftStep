package com.fitnessproject

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.passwordInput
import kotlinx.android.synthetic.main.activity_registration.usernameInput

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    fun genderButtonClicked(view: android.view.View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioMaleBtn ->
                    if (checked) { radioMaleBtn.setBackgroundResource(R.drawable.radio_left_checked)
                        radioFemaleBtn.setBackgroundResource(R.drawable.radio_right_unchecked)

                        radioMaleBtn.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                        radioFemaleBtn.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color

                    }
                R.id.radioFemaleBtn ->
                    if (checked) {
                        radioMaleBtn.setBackgroundResource(R.drawable.radio_left_unchecked)
                        radioFemaleBtn.setBackgroundResource(R.drawable.radio_right_checked)
                        radioMaleBtn.setTextColor(Color.parseColor("#FFFFFFFF")) //dark mode bg color
                        radioFemaleBtn.setTextColor(Color.parseColor("#303030")) //android white color
                    }
            }
        }
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

                        val genderRadioButtonText = when (registerGenderGroup.checkedRadioButtonId) { //getting text value of gender radio button
                            R.id.radioMaleBtn -> radioMaleBtn.text
                            else -> radioFemaleBtn.text
                        }

                        val activityLevelText = when (activityLevelGroup.checkedRadioButtonId){ //getting text value of selected activityLevelButton
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
                        setBMI()

                        Toast.makeText(this@RegistrationActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun setBMI() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        //setting BMI using height and weight given
        val heightInMetres = heightInput.text.toString().toDouble() / 100
        val weight = weightInput.text.toString().toDouble()
        val BMI = weight / (heightInMetres * heightInMetres)
        val roundedBMI = Math.round(BMI*100.0)/100.0

        currentUSerDb?.child("BMI")?.setValue(roundedBMI)
    }

    fun activityButtonClicked(view: android.view.View) { //function is checking to see which radio button in activityLevel group is checked so it can write the value to database later
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioLowActivity ->
                    if (checked) { radioLowActivity.setBackgroundResource(R.drawable.radio_left_checked)
                        radioModerateActivity.setBackgroundResource(R.drawable.radio_middle_unchecked)
                        radioHighActivity.setBackgroundResource(R.drawable.radio_right_unchecked)

                        radioLowActivity.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                        radioModerateActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color
                        radioHighActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color



                    }
                R.id.radioModerateActivity ->
                    if (checked) {
                        radioLowActivity.setBackgroundResource(R.drawable.radio_left_unchecked)
                        radioModerateActivity.setBackgroundResource(R.drawable.radio_middle_checked)
                        radioHighActivity.setBackgroundResource(R.drawable.radio_right_unchecked)

                        radioLowActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                        radioModerateActivity.setTextColor(Color.parseColor("#303030")) //darkmode bg color
                        radioHighActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                    }
                R.id.radioHighActivity ->
                    if (checked) {
                        radioLowActivity.setBackgroundResource(R.drawable.radio_left_unchecked)
                        radioModerateActivity.setBackgroundResource(R.drawable.radio_middle_unchecked)
                        radioHighActivity.setBackgroundResource(R.drawable.radio_right_checked)

                        radioLowActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                        radioModerateActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color
                        radioHighActivity.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                    }
            }
        }

    }

}
