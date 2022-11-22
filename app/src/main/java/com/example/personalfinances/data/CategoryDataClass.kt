package com.example.personalfinances.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "expanses")
    var expenses: Double?,
    @ColumnInfo(name = "icon")
    var icon: Int?,
    @ColumnInfo(name = "color")
    var color: Int?,
)
