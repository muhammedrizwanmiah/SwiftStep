package com.fitnessproject

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fitnessproject.fragments.ProfileFragment
import com.fitnessproject.fragments.StepsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    val ACTIVITY_RECOGNITION_RQ = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

//        checkForPermissions(android.Manifest.permission.ACTIVITY_RECOGNITION, "activityrecognition", ACTIVITY_RECOGNITION_RQ)

        val profileFragment = ProfileFragment()
        val stepsFragment = StepsFragment()

        makeCurrentFragment(profileFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_profile -> makeCurrentFragment(profileFragment)
                R.id.navigation_steps -> makeCurrentFragment(stepsFragment)
            }
            true
        }
    }

//    private fun checkForPermissions(permission: String, name: String, requestCode: Int){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            when {
//                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
//                   Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
//                }
//                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)
//
//                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        fun innerCheck(name: String){
//            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
//            }
//
//            when (requestCode){
//                ACTIVITY_RECOGNITION_RQ -> innerCheck("activityrecognition")
//            }
//        }
//    }
//
//    private fun showDialog(permission: String, name: String, requestCode: Int){
//        val builder = AlertDialog.Builder(this)
//
//        builder.apply{
//            setMessage("Permission to access your $name is required to use this app")
//            setTitle("Permission required")
//            setPositiveButton("OK"){dialog, which ->
//                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
//            }
//        }
//        val dialog = builder.create()
//        dialog.show()
//
//    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}