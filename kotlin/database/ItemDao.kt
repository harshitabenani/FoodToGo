package com.harshita.foodtogo.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao

interface ItemDao {

    @Insert
    fun insertItem(itemEntity: ItemEntity)

    @Delete
    fun deleteItem(itemEntity: ItemEntity)


    @Query("SELECT * FROM item WHERE item_id= :itemId")
    fun getItemById(itemId:String):ItemEntity

    @Query("SELECT * FROM item")
    fun getAllItems():MutableList<ItemEntity>

    @Query("DELETE FROM item")
    fun deleteAll(): Int



}