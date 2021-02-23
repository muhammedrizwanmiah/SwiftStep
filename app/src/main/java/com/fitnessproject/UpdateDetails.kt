package com.fitnessproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_update_details.*

class UpdateDetails : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_details)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadPage()

    }


    private fun loadPage() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        currentUSerDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentGender?.text = snapshot.child("gender").value.toString()
                currentActivityLevel?.text = snapshot.child("activitylevel").value.toString()
                //sets text field as current user values
                updateFirstNameInput?.setText(snapshot.child("firstname").value.toString())
                updateLastNameInput?.setText(snapshot.child("lastname").value.toString())
                updateAgeInput?.setText(snapshot.child("age").value.toString())
                updateHeightInput?.setText(snapshot.child("height").value.toString())
                updateWeightInput?.setText(snapshot.child("weight").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        updateDetailsButton.setOnClickListener {

            if(TextUtils.isEmpty(updateFirstNameInput.text.toString())) {
                updateFirstNameInput.setError("Please enter first name.")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(updateLastNameInput.text.toString())) {
                updateLastNameInput.setError("Please enter last name.")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(updateAgeInput.text.toString())) {
                updateAgeInput.setError("Please enter age.")
                return@setOnClickListener
            }
            else if(updateAgeInput.text.toString().toInt() < 16) {
                updateAgeInput.setError("Age cannot be less than 16.")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(updateHeightInput.text.toString())) {
                updateHeightInput.setError("Please enter height.")
                return@setOnClickListener
            }
            else if(updateHeightInput.text.toString().toInt() < 130) {
                updateHeightInput.setError("Minimum height accepted for this app is 130cm.")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(updateWeightInput.text.toString())) {
                weightInput.setError("Please enter weight")
                return@setOnClickListener
            }
            else if(updateWeightInput.text.toString().toInt() < 40) {
                updateWeightInput.setError("Minimum weight accepted for this app is 40kg.")
                return@setOnClickListener
            }

            val updateGenderRadioButtonText = when (updateGenderGroup.checkedRadioButtonId) { //getting text value of gender radio button
                R.id.updateRadioMaleBtn -> updateRadioMaleBtn.text
                else -> updateRadioFemaleBtn.text
            }

            val updateActivityLevelText = when (updateActivityLevelGroup.checkedRadioButtonId){ //getting text value of selected activityLevelButton
                R.id.updateRadioLowActivity -> updateRadioLowActivity.text
                R.id.updateRadioModerateActivity -> updateRadioModerateActivity.text
                R.id.updateRadioHighActivity -> updateRadioHighActivity.text
                else -> null
            }

            currentUSerDb?.child("firstname")?.setValue(updateFirstNameInput.text.toString())
            currentUSerDb?.child("lastname")?.setValue(updateLastNameInput.text.toString())
            currentUSerDb?.child("gender")?.setValue(updateGenderRadioButtonText.toString())
            currentUSerDb?.child("age")?.setValue(updateAgeInput.text.toString().toInt())
            currentUSerDb?.child("height")?.setValue(updateHeightInput.text.toString().toInt())
            currentUSerDb?.child("weight")?.setValue(updateWeightInput.text.toString().toInt())
            currentUSerDb?.child("activitylevel")?.setValue(updateActivityLevelText.toString())
            updateBMI()
        }
    }

    private fun updateBMI() {

        val currentUser = auth.currentUser
        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))

        //setting BMI using height and weight given
        val heightInMetres = updateHeightInput.text.toString().toDouble() / 100
        val weight = updateWeightInput.text.toString().toDouble()
        val BMI = weight / (heightInMetres * heightInMetres)
        val roundedBMI = Math.round(BMI*100.0)/100.0

        currentUSerDb?.child("BMI")?.setValue(roundedBMI)
    }

    fun updateGenderButtonClicked(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.updateRadioMaleBtn ->
                    if (checked) { updateRadioMaleBtn.setBackgroundResource(R.drawable.radio_left_checked)
                        updateRadioFemaleBtn.setBackgroundResource(R.drawable.radio_right_unchecked)

                        updateRadioMaleBtn.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                        updateRadioFemaleBtn.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color

                    }
                R.id.updateRadioFemaleBtn ->
                    if (checked) {
                        updateRadioMaleBtn.setBackgroundResource(R.drawable.radio_left_unchecked)
                        updateRadioFemaleBtn.setBackgroundResource(R.drawable.radio_right_checked)
                        updateRadioMaleBtn.setTextColor(Color.parseColor("#FFFFFFFF")) //dark mode bg color
                        updateRadioFemaleBtn.setTextColor(Color.parseColor("#303030")) //android white color
                    }
            }
        }
    }

    fun updateActivityButtonClicked(view: View) { //function is checking to see which radio button in activityLevel group is checked so it can write the value to database later
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.updateRadioLowActivity ->
                    if (checked) { updateRadioLowActivity.setBackgroundResource(R.drawable.radio_left_checked)
                        updateRadioModerateActivity.setBackgroundResource(R.drawable.radio_middle_unchecked)
                        updateRadioHighActivity.setBackgroundResource(R.drawable.radio_right_unchecked)

                        updateRadioLowActivity.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                        updateRadioModerateActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color
                        updateRadioHighActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color



                    }
                R.id.updateRadioModerateActivity ->
                    if (checked) {
                        updateRadioLowActivity.setBackgroundResource(R.drawable.radio_left_unchecked)
                        updateRadioModerateActivity.setBackgroundResource(R.drawable.radio_middle_checked)
                        updateRadioHighActivity.setBackgroundResource(R.drawable.radio_right_unchecked)

                        updateRadioLowActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                        updateRadioModerateActivity.setTextColor(Color.parseColor("#303030")) //darkmode bg color
                        updateRadioHighActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                    }
                R.id.updateRadioHighActivity ->
                    if (checked) {
                        updateRadioLowActivity.setBackgroundResource(R.drawable.radio_left_unchecked)
                        updateRadioModerateActivity.setBackgroundResource(R.drawable.radio_middle_unchecked)
                        updateRadioHighActivity.setBackgroundResource(R.drawable.radio_right_checked)

                        updateRadioLowActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //white color
                        updateRadioModerateActivity.setTextColor(Color.parseColor("#FFFFFFFF")) //android white color
                        updateRadioHighActivity.setTextColor(Color.parseColor("#303030")) //dark mode bg color
                    }
            }
        }

    }
}
