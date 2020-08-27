package com.harshita.foodtogo.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.harshita.foodtogo.R
import com.harshita.foodtogo.fragment.HomeFragment
import com.harshita.foodtogo.util.ConnectionManager

class RegisterActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etDelivery: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etDelivery = findViewById(R.id.etDelivery)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        toolbar = findViewById(R.id.toolbar)

        setUpToolbar()

        btnRegister.setOnClickListener {

            if (etName.text.toString()!= "" && etEmail.text.toString()!= "" && etDelivery.text.toString()!= "" && etMobileNumber.text.toString()!= ""&& etPassword.text.toString()!= "" && etConfirmPassword.text.toString()!= "") {
                if (etPassword.text.toString() == etConfirmPassword.text.toString()) {

                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    intent.putExtra("Name",etName.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Passwords do not match!!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else{

                Toast.makeText(
                    this@RegisterActivity,
                    "Please fill all fields!!!",
                    Toast.LENGTH_LONG
                ).show()

            }

        }

    }

    fun setUpToolbar() { //tool bar title
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    }



