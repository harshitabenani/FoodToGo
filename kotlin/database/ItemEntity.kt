package com.harshita.foodtogo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")

data class ItemEntity (
    @PrimaryKey val item_id:Int,
    @ColumnInfo(name="item_name") val itemName: String,
    @ColumnInfo(name="item_cost_for_one")val itemCostForOne: Int

)