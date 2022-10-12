package com.example.personalfinances.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name="fromId")
    var fromId: Int?,
    @ColumnInfo(name="toId")
    var toId: Int?,
    @ColumnInfo(name = "amount")
    var amount: Double?,
    @ColumnInfo(name = "date")
    var date: Date
)
