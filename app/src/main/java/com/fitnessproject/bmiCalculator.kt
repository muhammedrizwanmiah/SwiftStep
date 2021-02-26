package com.fitnessproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bmi_calculator.*

class bmiCalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        loadCalculatorPage()
    }

    private fun loadCalculatorPage() {

        calculateButton.setOnClickListener {

            val height = calcHeightInput.text.toString().toDouble() / 100
            val weight = calcWeightInput.text.toString().toDouble()
            val result = weight / (height * height)
            val roundedResult = Math.round(result*100.0)/100.0

            BMICircleText.text = roundedResult.toString()

            if (roundedResult >= 18.5 && roundedResult <= 25.0){
                BMICircleText.setTextColor(Color.parseColor("#66bb6a")) //if healthy weight, set text to green
            }
            else if (roundedResult < 18.5){
                BMICircleText.setTextColor(Color.parseColor("#ffee58")) //if underweight, set text to yellow
            }
            else if (roundedResult > 25 && roundedResult <= 30 ){
                BMICircleText.setTextColor(Color.parseColor("#ffb300")) //if overweight, set text to orange
            }
            else {
                BMICircleText.setTextColor(Color.parseColor("#ef5350")) //if obese, else set to red
            }

        }

    }
}
