package com.harshita.foodtogo.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.harshita.foodtogo.R
import com.harshita.foodtogo.activity.CartActivity
import com.harshita.foodtogo.activity.HomeActivity
import com.harshita.foodtogo.adapter.RestaurantDetailsRecyclerAdapter
import com.harshita.foodtogo.adapter.RestaurantDetailsRecyclerAdapter.Companion.isCartEmpty
import com.harshita.foodtogo.model.Item
import com.harshita.foodtogo.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_restaurant_details.view.*
import org.json.JSONException


class RestaurantDetailsFragment : Fragment() {

    lateinit var recyclerRestaurantDetails: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var btnGoToCart: Button

    lateinit var recyclerAdapter: RestaurantDetailsRecyclerAdapter

    val itemInfoList = arrayListOf<Item>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaurant_details, container, false)

        recyclerRestaurantDetails = view.findViewById(R.id.recyclerRestaurantDetails)
        btnGoToCart = view.findViewById(R.id.btnGoToCart)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        val resId = arguments?.getInt("id", 0)
        val resName = arguments?.getString("name", "Restaurant Details")

        btnGoToCart.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            intent.putExtra("rname", resName)
            context?.startActivity(intent)
        }



        if (ConnectionManager().CheckConnectivity(activity as Context)) {

            val queue = Volley.newRequestQueue(activity as? Context)
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,
                "http://13.235.250.119/v2/restaurants/fetch_result/$resId",
                null,
                Response.Listener {
                    try {
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            progressLayout.visibility = View.GONE
                            val resMenuData = data.getJSONArray("data")
                            for (i in 0 until resMenuData.length()) {
                                val jsonObject = resMenuData.getJSONObject(i)
                                val menuObject = Item(
                                    jsonObject.getString("id").toInt(),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("cost_for_one").toInt()
                                )
                                itemInfoList.add(menuObject)


                                recyclerAdapter =
                                    RestaurantDetailsRecyclerAdapter(
                                        activity as Context,
                                        itemInfoList
                                    )

                                recyclerRestaurantDetails.adapter = recyclerAdapter
                                layoutManager = LinearLayoutManager(activity)
                                recyclerRestaurantDetails.layoutManager = layoutManager


                            }
                        } else {
                            Toast.makeText(
                                activity as? Context,
                                "Some Error Occurred",
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

                        Toast.makeText(
                            activity as Context,
                            "Volley Error Occured",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {

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