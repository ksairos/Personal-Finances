    package com.example.personalfinances.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "balance")
    val balance: Double?,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,
    @ColumnInfo(name = "icon")
    var icon: Int?,
    @ColumnInfo(name = "color")
    var color: Int?,

)
