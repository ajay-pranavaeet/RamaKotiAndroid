package com.pramanam.ramakoti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LandingScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)

        val loginBtn = findViewById<Button>(R.id.login_btn)
        loginBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val registerBtn = findViewById<Button>(R.id.register_btn)
        registerBtn.setOnClickListener{
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        val guestBtn = findViewById<Button>(R.id.guest)
        guestBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
