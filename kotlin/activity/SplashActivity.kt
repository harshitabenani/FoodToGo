package com.harshita.foodtogo.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.harshita.foodtogo.BuildConfig
import com.harshita.foodtogo.R
import com.mhtmalpani.superextensions.primitive.orFalse

class SplashActivity : AppCompatActivity() {

    val sharedPreferences by lazy{
        application.getSharedPreferences(
            BuildConfig.APPLICATION_ID + "-global",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (supportActionBar != null)
            supportActionBar?.hide()

        Handler().postDelayed({
            val intent= if(sharedPreferences.getBoolean("userLoggedIn", false).orFalse()){
                 Intent(this@SplashActivity, HomeActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
        },2000)

    }
}