package com.harshita.foodtogo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.harshita.foodtogo.R

class SuccessfulActivity : AppCompatActivity() {

    lateinit var btnOk:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful)

        btnOk=findViewById(R.id.btnOk)

        btnOk.setOnClickListener {
            val intent = Intent(this@SuccessfulActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}