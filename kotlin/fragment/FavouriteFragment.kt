package com.harshita.foodtogo.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.harshita.foodtogo.R
import com.harshita.foodtogo.adapter.FavouriteRecyclerAdapter
import com.harshita.foodtogo.database.ResDatabase
import com.harshita.foodtogo.database.ResEntity


class FavouriteFragment : Fragment() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    lateinit var recyclerAdapter: FavouriteRecyclerAdapter

    var dbResList = listOf<ResEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_favourite, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        layoutManager = LinearLayoutManager(activity)

        dbResList=RetrieveFavourites(activity as Context).execute().get()


        if(activity!=null)
        {
            progressLayout.visibility=View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbResList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite .layoutManager = layoutManager

        }



        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<ResEntity>>(){

        override fun doInBackground(vararg p0: Void?): List<ResEntity> {
            val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()

            return db.resDao().getAllRes()
        }
    }


}