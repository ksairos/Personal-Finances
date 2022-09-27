package com.example.personalfinances

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "expanses")
    var expanses: Int = 0,
    @ColumnInfo(name = "icon")
    var icon: Int,
    @ColumnInfo(name = "color")
    var color: Int,
)

data class Transaction(
    val id: String,
)