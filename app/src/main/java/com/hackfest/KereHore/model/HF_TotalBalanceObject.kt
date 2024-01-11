package com.hackfest.KereHore.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import java.util.UUID.randomUUID

@DatabaseView
@Entity(tableName = "totalBalance_table")
data class HF_TotalBalanceObject(
    @PrimaryKey
    val totalBalanceID: UUID = UUID.randomUUID(),

    @ColumnInfo
    var totalBalance: String = "0.0",

    @ColumnInfo
    val balanceAvailableToAlocate: String = "0.0"
)