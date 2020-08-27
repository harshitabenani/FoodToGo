package com.harshita.foodtogo.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.harshita.foodtogo.R
import com.harshita.foodtogo.activity.CartActivity
import com.harshita.foodtogo.activity.HomeActivity
import com.harshita.foodtogo.database.ItemDatabase
import com.harshita.foodtogo.database.ItemEntity
import com.harshita.foodtogo.database.ResDatabase
import com.harshita.foodtogo.database.ResEntity
import com.harshita.foodtogo.model.Item
import com.harshita.foodtogo.model.Restaurants

class RestaurantDetailsRecyclerAdapter(val context: Context, val itemList: MutableList<Item>) :
    RecyclerView.Adapter<RestaurantDetailsRecyclerAdapter.RestaurantDetailsViewHolder>() {

    companion object{
        var isCartEmpty=true
    }

    class RestaurantDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtItemName: TextView = view.findViewById(R.id.txtItemName)
        val txtItemCostForOne: TextView = view.findViewById(R.id.txtItemCostForOne)
        val btnAdd: Button = view.findViewById(R.id.btnAdd)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantDetailsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_details_recyclerview_single_row, parent, false)
        return RestaurantDetailsRecyclerAdapter.RestaurantDetailsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }



    override fun onBindViewHolder(holder: RestaurantDetailsViewHolder, position: Int) {

        val item = itemList[position]
        holder.txtItemName.text = item.itemName
        holder.txtItemCostForOne.text = "Rs.${item.itemCostForOne.toString()}"









        val itemEntity = ItemEntity(   // add button
            item.itemId,
            item.itemName,
            item.itemCostForOne
        )
        val checkFav =
            RestaurantDetailsRecyclerAdapter.DBAsyncTask(context, itemEntity, 1).execute()
        val isFav = checkFav.get()

        if (isFav) {
            holder.btnAdd.text = "REMOVE"
            val favColor = ContextCompat.getColor(
                context,
                R.color.colorPrimary
            )
            holder.btnAdd.setBackgroundColor(favColor)
        } else {
            holder.btnAdd.text = "ADD"
            val noFavColor =
                ContextCompat.getColor(context, R.color.colorFavourites)
            holder.btnAdd.setBackgroundColor(noFavColor)
        }

        holder.btnAdd.setOnClickListener {

            if (!DBAsyncTask(context, itemEntity, 1).execute()
                    .get()
            )   // if book is not added in the faves and user wants to add
            {
                val async = DBAsyncTask(context, itemEntity, 2).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Item added to Cart",
                        Toast.LENGTH_SHORT
                    ).show()

                    holder.btnAdd.text = "REMOVE"
                    val favColor = ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                    holder.btnAdd.setBackgroundColor(favColor)

                } else {
                    Toast.makeText(
                        context,
                        "Could not add item due to some Error!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {   //if book is added in th faves and user wants to remove

                val async = DBAsyncTask(context, itemEntity, 3).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Item removed from Cart",
                        Toast.LENGTH_SHORT
                    ).show()

                    holder.btnAdd.text = "ADD"
                    val noFavColor =
                        ContextCompat.getColor(context, R.color.colorFavourites)
                    holder.btnAdd.setBackgroundColor(noFavColor)

                } else {
                    Toast.makeText(
                        context,
                        "Could not remove item due to some Error!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

    }


    class DBAsyncTask(val context: Context, val itemEntity: ItemEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        /*
        Mode 1: check db if res is in fave or not
        mode 2: save a res in fave
        mode 3: remove a res from fave
         */

        val db = Room.databaseBuilder(context, ItemDatabase::class.java, "items-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {

            when (mode) {
                1 -> {

                    val item: ItemEntity? = db.itemDao().getItemById(itemEntity.item_id.toString())
                    db.close()
                    return item != null //if it is null then false is returned
                }

                2 -> {
                    db.itemDao().insertItem(itemEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.itemDao().deleteItem(itemEntity)
                    db.close()
                    return true
                }

            }
            return false
        }


    }
}
