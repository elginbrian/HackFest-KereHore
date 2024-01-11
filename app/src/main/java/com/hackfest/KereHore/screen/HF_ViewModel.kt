package com.hackfest.KereHore.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackfest.KereHore.database.HF_DatabaseDao
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.repository.HF_Repository
import com.hackfest.KereHore.repository.HF_Repository2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

    @HiltViewModel
    class HF_ViewModel @Inject constructor(val HF_Repository: HF_Repository, val HF_DatabaseDao: HF_DatabaseDao): ViewModel() {

        val _pocketList = MutableStateFlow<List<HF_PocketObject>>(emptyList())
        val pocketList  = _pocketList.asStateFlow()

        init {
            viewModelScope.launch(Dispatchers.IO) {
                HF_Repository.getAllPocket().distinctUntilChanged().collect{
                        listOfPocket -> if(listOfPocket.isNullOrEmpty()){

                        } else {
                            _pocketList.value = listOfPocket
                        }
                }
            }
        }

        fun reportSpending(
            pocketID: UUID,
            pocketSpending: String,
            pocketBalance: String
        )                                                       = viewModelScope.launch { HF_Repository.reportSpending(pocketID, pocketSpending, pocketBalance) }
        fun addPocket(HF_PocketObject: HF_PocketObject)         = viewModelScope.launch { HF_Repository.addPocket(HF_PocketObject)    }
        fun removePocket(pocketID: UUID)                        = viewModelScope.launch { HF_Repository.deletePocket(pocketID) }
        fun updatePocket(pocketID: UUID, pocketBalance: String) = viewModelScope.launch { HF_Repository.updatePocket(pocketID, pocketBalance) }
        fun deleteAllPocket(HF_PocketObject: HF_PocketObject)   = viewModelScope.launch { HF_Repository.deleteAllPocket() }
    }

    @HiltViewModel
    class HF_ViewModel2 @Inject constructor(val HF_Repository2: HF_Repository2): ViewModel() {
        val _totalBalance = MutableStateFlow<List<HF_TotalBalanceObject>>(emptyList())
        val totalBalance = _totalBalance.asStateFlow()

        init {
            viewModelScope.launch(Dispatchers.IO) {
                HF_Repository2.getAllTotalBalance().distinctUntilChanged().collect{
                        listOfTotalBalance -> if(listOfTotalBalance.isNullOrEmpty()) {
                            //
                        } else {
                            _totalBalance.value = listOfTotalBalance
                        }
                }
            }
        }

        fun addTotalBalance(HF_TotalBalanceObject: HF_TotalBalanceObject)    = viewModelScope.launch { HF_Repository2.addTotalBalance(HF_TotalBalanceObject) }
        fun removeTotalBalance(HF_TotalBalanceObject: HF_TotalBalanceObject) = viewModelScope.launch { HF_Repository2.deleteTotalBalance(HF_TotalBalanceObject) }
        fun deleteAllBalance(HF_TotalBalanceObject: HF_TotalBalanceObject)   = viewModelScope.launch { HF_Repository2.deleteAllBalance(HF_TotalBalanceObject) }
        fun updateTotalBalance(totalBalanceID: UUID, totalBalance: String)   = viewModelScope.launch { HF_Repository2.updateTotalBalance(totalBalanceID, totalBalance) }
    }
