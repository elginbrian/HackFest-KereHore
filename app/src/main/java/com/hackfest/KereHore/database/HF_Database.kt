package com.hackfest.KereHore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.util.ListConverter
import com.hackfest.KereHore.util.UUIDConverter

@Database(entities = [HF_PocketObject::class], version = 7, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class HF_Database: RoomDatabase(){
    abstract fun HF_DatabaseDao(): HF_DatabaseDao
}

@Database(entities = [HF_TotalBalanceObject::class], version = 7, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class HF_Database2: RoomDatabase(){
    abstract fun HF_DatabaseDao2(): HF_DatabaseDao2
}