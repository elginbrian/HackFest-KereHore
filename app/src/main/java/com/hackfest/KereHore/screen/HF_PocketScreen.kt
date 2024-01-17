package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_CircleButton
import com.hackfest.KereHore.widgets.HF_PopUpCard
import com.hackfest.KereHore.widgets.HF_TextField
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HF_PocketScreen(
    navController: NavController = rememberNavController(),
    pocketID: String?,
    pocketList: List<HF_PocketObject>,
    totalBalance: List<HF_TotalBalanceObject>,

    addPocketBalance: (Pair<UUID, String>) -> Unit,
    reportSpending: (Triple<UUID, String, String>) -> Unit,
    deleteThisPocket: (UUID) -> Unit,
    //updateTotalBalance: (Pair<UUID, String>) -> Unit
    addTotalBalance: (HF_TotalBalanceObject) -> Unit
){

    //val pocket = HF_GetDummies().filter { HF_DummyTemplate -> HF_DummyTemplate.pocketID == pocketTitle}
    val pocket  = pocketList.filter { pocket -> pocket.pocketID.toString().equals(pocketID) }
    val context = LocalContext.current as Activity

    var displayAssignForm = remember {
        mutableStateOf(false)
    }
    var displayReportForm = remember {
        mutableStateOf(false)
    }
    var displayHistory = remember {
        mutableStateOf(false)
    }
    var newPocketBalance = remember {
        mutableStateOf("")
    }
    var newReportedSpending = remember {
        mutableStateOf("")
    }
    var exceptionString = remember {
        mutableStateOf("")
    }

    var currentTotalBalance: String
    if(totalBalance.size == 0){
        currentTotalBalance = "0.0"
    } else {
        currentTotalBalance = totalBalance[totalBalance.size - 1].totalBalance
    }

    var listOfPocketHistory = pocket[0].pocketHistory.split("-")

    if(listOfPocketHistory.size > 1){
        listOfPocketHistory = listOfPocketHistory.subList(1, listOfPocketHistory.size)
    } else {
        listOfPocketHistory = listOf()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold (
            topBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HF_CircleButton(imageVector = Icons.Default.ArrowBack){ trigger ->
                            if(trigger.isNotEmpty()){
                                navController.navigate(route = HF_NavEnum.HF_HomeScreen.name)
                            }
                        }

                        Text(text = pocket[0].pocketTitle, fontSize = 20.sp)

                            HF_CircleButton(imageVector = Icons.Default.Delete){ trigger ->
                                if(trigger.isNotEmpty()){
                                    navController.navigate(route = HF_NavEnum.HF_HomeScreen.name)
                                    deleteThisPocket(pocket[0].pocketID)
                                }
                            }

                    }
                }
            },

            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 75.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Rp.${pocket[0].pocketBalance}", fontSize = 25.sp, fontWeight = FontWeight(500))

                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Spacer(modifier = Modifier.padding(5.dp))
                                if(pocket[0].pocketBalance.toDouble() < 1){
                                    HF_CircleButton(imageVector = Icons.Default.AddCircle){trigger ->
                                        if(trigger.isNotEmpty()){
                                            displayAssignForm.value = !displayAssignForm.value
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(displayHistory.value == true){
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 15.dp)){
                            Column(
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Spending History", fontSize = 20.sp)
                            }
                            LazyColumn(modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)){
                                items(listOfPocketHistory){pocketHistory ->
                                    Card(modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(bottom = 10.dp),
                                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                                    ){
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(text = pocketHistory)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },

            bottomBar = {
                if (displayAssignForm.value == false && displayReportForm.value == false){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            HF_CircleButton(imageVector = Icons.Default.Create) { trigger ->
                                if(trigger.isNotEmpty()){
                                    exceptionString.value = "under development"
                                }
                            }
                            HF_CircleButton(imageVector = Icons.Default.Add) {trigger ->
                                if(trigger.isNotEmpty()){
                                    displayReportForm.value = !displayReportForm.value
                                }
                            }
                            HF_CircleButton(imageVector = Icons.Default.List){trigger ->
                                if(trigger.isNotEmpty()){
                                    displayHistory.value = !displayHistory.value
                                }
                            }
                        }
                    }

                } else if (displayAssignForm.value == true){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text("Alocate balance to this pocket", fontSize = 20.sp)
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_TextField(
                                keyboardType = KeyboardType.Number,
                                text  = newPocketBalance.value,
                                label = "Input",
                                onTextChange = {
                                    if (it.all { char -> char.isDigit() }){
                                        newPocketBalance.value = it
                                    }
                                })
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_CircleButton(imageVector = Icons.Default.Check){trigger ->
                                if (trigger.isNotEmpty()) {
                                    pocket[0].pocketBalance = newPocketBalance.value
                                    addPocketBalance(Pair(pocket[0].pocketID, pocket[0].pocketBalance))

                                    newPocketBalance.value = ""
                                    displayAssignForm.value = !displayAssignForm.value
                                    navController.navigate(route = HF_NavEnum.HF_PocketScreen.name+"/${pocket[0].pocketID.toString()}")
                                }
                            }
                        }
                    }
                } else if (displayReportForm.value == true) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text("Report your spending", fontSize = 20.sp)
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_TextField(
                                keyboardType = KeyboardType.Number,
                                text  = newReportedSpending.value,
                                label = "Input",
                                onTextChange = {
                                    if (it.all { char -> char.isDigit() }){
                                        newReportedSpending.value = it
                                    }
                                })
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_CircleButton(imageVector = Icons.Default.Check){trigger ->
                                if (trigger.isNotEmpty()) {
                                    if(newReportedSpending.value.toDouble() <= pocket[0].pocketBalance.toDouble()) {
                                        pocket[0].pocketBalance =
                                            (pocket[0].pocketBalance.toDouble() - newReportedSpending.value.toDouble()).toString()
                                        currentTotalBalance =
                                            (currentTotalBalance.toDouble() - newReportedSpending.value.toDouble()).toString()

                                        reportSpending(
                                            Triple(
                                                pocket[0].pocketID,
                                                newReportedSpending.value,
                                                pocket[0].pocketBalance
                                            )
                                        )
                                        //updateTotalBalance(Pair(totalBalance[totalBalance.size - 1].totalBalanceID, currentTotalBalance))
                                        addTotalBalance(HF_TotalBalanceObject(totalBalance = currentTotalBalance))

                                        newReportedSpending.value = ""
                                        displayReportForm.value = !displayReportForm.value
                                        navController.navigate(route = HF_NavEnum.HF_PocketScreen.name + "/${pocket[0].pocketID.toString()}")
                                    } else {
                                        newReportedSpending.value = ""
                                        displayReportForm.value = !displayReportForm.value
                                        exceptionString.value = "invalid report"
                                    }
                                }
                            }
                        }
                    }
                }
            },

            snackbarHost = ({
                if(!exceptionString.value.equals("")){
                    HF_PopUpCard(
                        text = when(exceptionString.value){
                            "invalid report" -> "Spending report can't be smaller than the pocket balance"
                            "under development" -> "This feature is not finished yet"
                            else -> ""
                        },
                        trigger = {trigger ->
                            if(trigger.isNotEmpty()){
                                exceptionString.value = ""
                            }
                        }
                    )
                }
            })
        )
    }
}