package com.fitnessproject

import android.view.View
import kotlinx.android.synthetic.main.activity_steps_badges.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

if(percentage10kSteps >= 60){
    steps10k.setImageResource(R.drawable.steps10k)
    steps10kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
    steps10kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    steps10kProgressLayout.visibility = (View.INVISIBLE)
    steps10kProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    steps10k.setImageResource(R.drawable.steps10ku)
    steps10kLayout.setBackgroundResource(R.drawable.card_body)
    steps10kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    steps10kProgressLayout.visibility = (View.VISIBLE)
    steps10kProgressCheckLayout.visibility = (View.INVISIBLE)
}

if(percentage20kSteps >= 60){
    steps20k.setImageResource(R.drawable.steps20k)
    steps20kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
    steps20kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    steps20kProgressLayout.visibility = (View.INVISIBLE)
    steps20kProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    steps20k.setImageResource(R.drawable.steps20ku)
    steps20kLayout.setBackgroundResource(R.drawable.card_body)
    steps20kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    steps20kProgressLayout.visibility = (View.VISIBLE)
    steps20kProgressCheckLayout.visibility = (View.INVISIBLE)
}

if(percentage30kSteps >= 60){
    steps30k.setImageResource(R.drawable.steps30k)
    steps30kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
    steps30kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    steps30kProgressLayout.visibility = (View.INVISIBLE)
    steps30kProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    steps30k.setImageResource(R.drawable.steps30ku)
    steps30kLayout.setBackgroundResource(R.drawable.card_body)
    steps30kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    steps30kProgressLayout.visibility = (View.VISIBLE)
    steps30kProgressCheckLayout.visibility = (View.INVISIBLE)
}

if(percentage40kSteps >= 60){
    steps40k.setImageResource(R.drawable.steps40k)
    steps40kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
    steps40kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    steps40kProgressLayout.visibility = (View.INVISIBLE)
    steps40kProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    steps40k.setImageResource(R.drawable.steps40ku)
    steps40kLayout.setBackgroundResource(R.drawable.card_body)
    steps40kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    steps40kProgressLayout.visibility = (View.VISIBLE)
    steps40kProgressCheckLayout.visibility = (View.INVISIBLE)
}

if(percentage60kSteps >= 60){
    steps60k.setImageResource(R.drawable.steps60k)
    steps60kLayout.setBackgroundResource(R.drawable.card_achievement_checked)
    steps60kInfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    steps60kProgressLayout.visibility = (View.INVISIBLE)
    steps60kProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    steps60k.setImageResource(R.drawable.steps60ku)
    steps60kLayout.setBackgroundResource(R.drawable.card_body)
    steps60kInfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    steps60kProgressLayout.visibility = (View.VISIBLE)
    steps60kProgressCheckLayout.visibility = (View.INVISIBLE)
}