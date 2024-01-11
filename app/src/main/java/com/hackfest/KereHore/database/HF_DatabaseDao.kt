package com.hackfest.KereHore.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import java.util.UUID

@Dao
interface HF_DatabaseDao {
    @Query("SELECT * from pocket_table")
    fun getPocket(): kotlinx.coroutines.flow.Flow<List<HF_PocketObject>>

    @Query("SELECT * from pocket_table where pocketID=:pocketID")
    suspend fun getPocketID(pocketID: UUID): HF_PocketObject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(HF_PocketObject: HF_PocketObject)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(HF_PocketObject: HF_PocketObject)

    @Query("DELETE from pocket_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deletePocket(HF_PocketObject: HF_PocketObject)
}

@Dao
interface HF_DatabaseDao2 {
    @Query("SELECT * from totalBalance_table")
    fun getTotalBalance(): kotlinx.coroutines.flow.Flow<List<HF_TotalBalanceObject>>

    @Query("SELECT * from totalBalance_table where totalBalanceID=:totalBalanceID")
    fun getTotalBalanceID(totalBalanceID: UUID): HF_TotalBalanceObject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(HF_TotalBalanceObject: HF_TotalBalanceObject)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(HF_TotalBalanceObject: HF_TotalBalanceObject)

    @Query("DELETE from totalBalance_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTotalBalance(HF_TotalBalanceObject: HF_TotalBalanceObject)
}