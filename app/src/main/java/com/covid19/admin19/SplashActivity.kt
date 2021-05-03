package com.covid19.admin19

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val sharedPreference: SharedPreferences? =
            getSharedPreferences("admin", Context.MODE_PRIVATE)
        var intent: Intent

        if (sharedPreference?.contains("City") == true) intent =
            Intent(this, MainActivity::class.java)
        else intent = Intent(this, StartActivity::class.java)


        Handler().postDelayed(
            {
                startActivity(intent)
                overridePendingTransition(R.anim.leftout, R.anim.rightin)
                finish()
            }, 2000
        )
    }
}