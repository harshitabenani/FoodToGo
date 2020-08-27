package com.harshita.foodtogo.adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.harshita.foodtogo.R
import com.harshita.foodtogo.activity.HomeActivity
import com.harshita.foodtogo.database.ResDatabase
import com.harshita.foodtogo.database.ResEntity
import com.harshita.foodtogo.fragment.RestaurantDetailsFragment
import com.harshita.foodtogo.model.Restaurants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*

class HomeRecyclerAdapter(val context:Context,val itemList:ArrayList<Restaurants>) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>(){

    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtResName :TextView= view.findViewById(R.id.txtResName)
        val txtResCostForOne:TextView= view.findViewById(R.id.txtResCostForOne)
        val txtResRating :TextView= view.findViewById(R.id.txtResRating)
        val imgResImage:ImageView= view.findViewById(R.id.imgResImage)

        val llHomeContent: LinearLayout=view.findViewById(R.id.llHomeContent)
        val imgHeart:ImageView=view.findViewById(R.id.imgHeart)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_recyclerview_single_row,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val res=itemList[position]
        holder.txtResName.text=res.resName
        holder.txtResRating.text=res.resRating
        val costForTwo = "Rs.${res.resCostForOne.toString()}/person"
        holder.txtResCostForOne.text= costForTwo
        Picasso.get().load(res.resImage).into(holder.imgResImage)

        holder.llHomeContent.setOnClickListener {

            val fragment = RestaurantDetailsFragment()
            val args = Bundle()
            args.putInt("id", res.resId as Int)
            args.putString("name", res.resName)
            fragment.arguments = args
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
            (context as AppCompatActivity).supportActionBar?.title = holder.txtResName.text.toString()



        }



        val resEntity = ResEntity(
            res.resId,
            res.resName,
            res.resRating,
            res.resCostForOne.toString(),
            res.resImage
        )


        val checkFav = DBAsyncTask(context, resEntity, 1).execute()
        val isFav = checkFav.get()

        if (isFav) {

            holder.imgHeart.setImageResource(R.drawable.ic_heart_fill)

        } else {

            holder.imgHeart.setImageResource(R.drawable.ic_heart)

        }


        holder.imgHeart.setOnClickListener {


            if (!DBAsyncTask(context, resEntity, 1).execute()
                    .get()
            )   // if book is not added in the faves and user wants to add
            {
                val async = DBAsyncTask(context, resEntity, 2).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Restaurant added to Favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgHeart.setImageResource(R.drawable.ic_heart_fill)

                } else {
                    Toast.makeText(
                        context,
                        "Could not add restaurant due to some Error!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {   //if book is added in th faves and user wants to remove

                val async = DBAsyncTask(context, resEntity, 3).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Restaurant removed from Favourites",
                        Toast.LENGTH_SHORT
                    ).show()

                    holder.imgHeart.setImageResource(R.drawable.ic_heart)

                } else {
                    Toast.makeText(
                        context,
                        "Could not remove restaurant due to some Error!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }



        }









    class DBAsyncTask(val context: Context, val ResEntity: ResEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        /*
        Mode 1: check db if res is in fave or not
        mode 2: save a res in fave
        mode 3: remove a res from fave
         */

        val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {

            when (mode) {
                1 -> {

                    val res: ResEntity? = db.resDao().getResById(ResEntity.res_id.toString())
                    db.close()
                    return res != null //if it is null then false is returned
                }

                2 -> {
                    db.resDao().insertRes(ResEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.resDao().deleteRes(ResEntity)
                    db.close()
                    return true
                }

            }
            return false
        }


    }

}


