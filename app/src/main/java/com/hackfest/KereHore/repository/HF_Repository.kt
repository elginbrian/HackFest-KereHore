package com.hackfest.KereHore.repository

import com.hackfest.KereHore.database.HF_DatabaseDao
import com.hackfest.KereHore.database.HF_DatabaseDao2
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class HF_Repository @Inject constructor(private val HF_DatabaseDao: HF_DatabaseDao) {

    suspend fun addPocket(HF_PocketObject: HF_PocketObject)        = HF_DatabaseDao.insert(HF_PocketObject)

    suspend fun updatePocket(pocketID: UUID, pocketBalance: String){
        var existingPocket = HF_DatabaseDao.getPocketID(pocketID)
        existingPocket.pocketBalance = pocketBalance

        HF_DatabaseDao.update(existingPocket)
    }
    suspend fun reportSpending(pocketID: UUID, pocketSpending: String, pocketBalance: String){
        var existingPocket = HF_DatabaseDao.getPocketID(pocketID)
        existingPocket.pocketHistory = "${existingPocket.pocketHistory}-${pocketSpending}"
        existingPocket.pocketBalance = pocketBalance

        HF_DatabaseDao.update(existingPocket)
    }

    suspend fun deletePocket(pocketID: UUID){
        var existingPocket = HF_DatabaseDao.getPocketID(pocketID)

        HF_DatabaseDao.deletePocket(existingPocket)
    }
    suspend fun deleteAllPocket()                                  = HF_DatabaseDao.deleteAll()
    fun getAllPocket(): Flow<List<HF_PocketObject>>                = HF_DatabaseDao.getPocket().flowOn(Dispatchers.IO).conflate()
}

class HF_Repository2 @Inject constructor(private val HF_DatabaseDao2: HF_DatabaseDao2){

    suspend fun addTotalBalance(HF_TotalBalanceObject: HF_TotalBalanceObject)    = HF_DatabaseDao2.insert(HF_TotalBalanceObject)

    suspend fun updateTotalBalance(totalBalanceID: UUID, totalBalance: String){
        var existingTotalBalance = HF_DatabaseDao2.getTotalBalanceID(totalBalanceID)
        existingTotalBalance.totalBalance = totalBalance

        HF_DatabaseDao2.update(existingTotalBalance)
    }
    suspend fun deleteTotalBalance(HF_TotalBalanceObject: HF_TotalBalanceObject) = HF_DatabaseDao2.deleteTotalBalance(HF_TotalBalanceObject)
    suspend fun deleteAllBalance(HF_TotalBalanceObject: HF_TotalBalanceObject)   = HF_DatabaseDao2.deleteAll()
    fun getAllTotalBalance(): Flow<List<HF_TotalBalanceObject>>                  = HF_DatabaseDao2.getTotalBalance().flowOn(Dispatchers.IO).conflate()
}