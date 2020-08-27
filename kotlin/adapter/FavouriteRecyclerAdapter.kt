package com.harshita.foodtogo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harshita.foodtogo.R
import com.harshita.foodtogo.database.ResEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val resList: List<ResEntity>) :
    RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {


    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtFavResName: TextView = view.findViewById(R.id.txtFavResName)
        val txtFavResCostForOne: TextView = view.findViewById(R.id.txtFavResCostForOne)
        val txtFavResRating: TextView = view.findViewById(R.id.txtFavResRating)
        val imgFavImage: ImageView = view.findViewById(R.id.imgFavImage)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_recyclerview_single_row, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val res = resList[position]
        holder.txtFavResName.text = res.resName
        holder.txtFavResRating.text = res.resRating
        val costForTwo = "Rs.${res.resCostForOne.toString()}/person"
        holder.txtFavResCostForOne.text= costForTwo
        Picasso.get().load(res.resImage)
            .into(holder.imgFavImage);

    }
}