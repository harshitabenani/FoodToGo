package com.harshita.foodtogo.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.harshita.foodtogo.R
import com.harshita.foodtogo.adapter.HomeRecyclerAdapter
import com.harshita.foodtogo.database.ResDatabase
import com.harshita.foodtogo.database.ResEntity
import com.harshita.foodtogo.model.Restaurants
import com.harshita.foodtogo.util.ConnectionManager
import com.mhtmalpani.superextensions.custom.toast
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {


    lateinit var recyclerHome: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar


    lateinit var recyclerAdapter: HomeRecyclerAdapter

    val resInfoList = arrayListOf<Restaurants>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)




        recyclerHome = view.findViewById(R.id.recyclerHome)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE


        if (ConnectionManager().CheckConnectivity(activity as Context)) {

            layoutManager = LinearLayoutManager(activity)

            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result/"
            val jsonObjectRequest =
                object : JsonObjectRequest(
                    Request.Method.GET, url, null, Response.Listener<JSONObject>
                    {


                        try {
                            progressLayout.visibility = View.GONE
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {

                                val resArray = data.getJSONArray("data")
                                for (i in 0 until resArray.length()) {
                                    val resObject = resArray.getJSONObject(i)
                                    val restaurant = Restaurants(
                                        resObject.getString("id").toInt(),
                                        resObject.getString("name"),
                                        resObject.getString("rating"),
                                        resObject.getString("cost_for_one").toInt(),
                                        resObject.getString("image_url")
                                    )
                                    resInfoList.add(restaurant)

                                    recyclerAdapter =
                                        HomeRecyclerAdapter(activity as Context, resInfoList)
                                    recyclerHome.adapter = recyclerAdapter
                                    recyclerHome.layoutManager = layoutManager

                                }


                            } else {
                                Toast.makeText(
                                    activity as Context,
                                    "Some Error Occured",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(
                                activity as Context,
                                "Some unexpected Error Occured",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    },
                    Response.ErrorListener {
                        if (activity != null) {
                                toast("Volley Error Occured")
                        }
                    }) {

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "2ee809e0412d93"
                        return headers
                    }
                }

            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()

        }




        return view
    }

}


