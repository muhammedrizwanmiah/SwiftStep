package com.fitnessproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        tv_user_id.text = "User ID = $userId"
        tv_email.text = "Email = $emailId"

        btn_logout.setOnClickListener {
            // Logs out from app
            FirebaseAuth.getInstance().signOut()
            //moves from main activity to login activity
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}
