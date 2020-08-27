package com.harshita.foodtogo.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.harshita.foodtogo.R
import com.harshita.foodtogo.BuildConfig
import com.mhtmalpani.superextensions.custom.execute


class LoginActivity : AppCompatActivity() {

    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnForgottenPassword: Button
    lateinit var btnSignUp: Button

    val sharedPreferences by lazy{ application.getSharedPreferences(BuildConfig.APPLICATION_ID + "-global",Context.MODE_PRIVATE)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null)
            supportActionBar?.hide()

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnForgottenPassword = findViewById(R.id.btnForgottenPassword)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            setUserLoggedIn()

            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra("phone", etMobileNumber.text.toString())
            startActivity(intent)

        }


    }

    private fun setUserLoggedIn(){

        sharedPreferences.execute {putBoolean("userLoggedIn", true)}

    }
}