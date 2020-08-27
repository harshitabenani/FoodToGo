package com.harshita.foodtogo.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.harshita.foodtogo.R
import com.harshita.foodtogo.adapter.CartRecyclerAdapter
import com.harshita.foodtogo.database.ItemDatabase
import com.harshita.foodtogo.database.ItemEntity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.restaurant_details_recyclerview_single_row.*

class CartActivity : AppCompatActivity() {

    lateinit var recyclerItem: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var txtOrder:TextView
    var message:String?=null
    lateinit var btnPlaceOrder: Button
    lateinit var txtCartItemCostForOne: TextView



    lateinit var recyclerAdapter: CartRecyclerAdapter

    var dbItemList = mutableListOf<ItemEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerItem = findViewById(R.id.recyclerItem)
        progressLayout=findViewById(R.id.progressLayout)
        progressBar=findViewById(R.id.progressBar)
        layoutManager = LinearLayoutManager(this@CartActivity)
        toolbar = findViewById(R.id.toolbar)
        txtOrder=findViewById(R.id.txtOrder)
        btnPlaceOrder=findViewById(R.id.btnPlaceOrder)






       btnPlaceOrder.setOnClickListener {
           val intent = Intent(this@CartActivity, SuccessfulActivity::class.java)
        startActivity(intent)
           DeleteItems(this@CartActivity).execute().get()
       }


        setUpToolbar()

        if(intent!=null)
        {
            message=intent.getStringExtra("rname")
            txtOrder.setText("Ordering From: ${message}")
        }

        dbItemList= RetrieveItems(this@CartActivity).execute().get()

        if(dbItemList.isEmpty())
        {
            btnPlaceOrder.visibility = View.GONE
        }

        if(this@CartActivity!=null)
        {
            progressLayout.visibility= View.GONE
            recyclerAdapter = CartRecyclerAdapter(this@CartActivity, dbItemList)
            recyclerItem.adapter = recyclerAdapter
            recyclerItem .layoutManager = layoutManager

        }





    }

    class RetrieveItems(val context: Context): AsyncTask<Void, Void, MutableList<ItemEntity>>(){

        override fun doInBackground(vararg p0: Void?): MutableList<ItemEntity> {
            val db = Room.databaseBuilder(context, ItemDatabase::class.java, "items-db").build()

            return db.itemDao().getAllItems()
        }
    }
    class DeleteItems(val context: Context): AsyncTask<Void, Void, Int>(){

        override fun doInBackground(vararg p0: Void?): Int{
            val db = Room.databaseBuilder(context, ItemDatabase::class.java, "items-db").build()

            return db.itemDao().deleteAll()
        }
    }



    fun setUpToolbar() {   //tool bar title
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cart"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }

    override fun onBackPressed(){

        DeleteItems(this@CartActivity).execute().get()

       super.onBackPressed()


    }



}