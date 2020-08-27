package com.harshita.foodtogo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harshita.foodtogo.R
import com.harshita.foodtogo.database.ItemEntity

import com.squareup.picasso.Picasso

class CartRecyclerAdapter(val context: Context, val itemList: List<ItemEntity>) :
    RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>() {




    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCartItemName: TextView = view.findViewById(R.id.txtCartItemName)
        val txtCartItemCostForOne: TextView = view.findViewById(R.id.txtCartItemCostForOne)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_recyclerview_single_row, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val item = itemList[position]
        holder.txtCartItemName.text = item.itemName
        holder.txtCartItemCostForOne.text = "Rs.${item.itemCostForOne.toString()}"






    }




}