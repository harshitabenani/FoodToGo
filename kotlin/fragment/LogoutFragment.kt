package com.harshita.foodtogo.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.harshita.foodtogo.R
import com.harshita.foodtogo.activity.CartActivity
import com.harshita.foodtogo.activity.HomeActivity
import com.harshita.foodtogo.activity.LoginActivity


class LogoutFragment : Fragment() {

    lateinit var btnYes:Button
    lateinit var btnCancel:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_logout, container, false)

        btnYes=view.findViewById(R.id.btnYes)
        btnCancel=view.findViewById(R.id.btnCancel)

        btnYes.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)

            context?.startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)

            context?.startActivity(intent)
        }


        return view
    }
}

