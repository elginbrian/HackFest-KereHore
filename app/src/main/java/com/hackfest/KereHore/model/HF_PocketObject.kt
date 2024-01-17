package com.hackfest.KereHore.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@DatabaseView
@Entity(tableName = "pocket_table")
data class HF_PocketObject(
    @PrimaryKey
    val pocketID: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "pocket_name")
    val pocketTitle: String = "New Pocket",

    @ColumnInfo(name = "pocket_description")
    val pocketDescription: String = "no description",

    @ColumnInfo(name = "pocket_balance")
    var pocketBalance: String = "0.0",

    @ColumnInfo(name = "pocket_history")
    var pocketHistory: String = "",

    //@ColumnInfo(name = "pocket_timestamp")
    //var pocketTimestamp: String = "",
)