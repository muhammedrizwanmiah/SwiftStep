package com.fitnessproject

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fitnessproject.fragments.AchievementsFragment
import com.fitnessproject.fragments.ProfileFragment
import com.fitnessproject.fragments.StatsFragment
import com.fitnessproject.fragments.StepsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_steps.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val profileFragment = ProfileFragment()
        val stepsFragment = StepsFragment()
        val statsFragment = StatsFragment()
        val achievementsFragment = AchievementsFragment()

        makeCurrentFragment(profileFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_profile -> makeCurrentFragment(profileFragment)
                R.id.navigation_steps -> makeCurrentFragment(stepsFragment)
                R.id.navigation_stats -> makeCurrentFragment(statsFragment)
                R.id.navigation_achievements -> makeCurrentFragment(achievementsFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}