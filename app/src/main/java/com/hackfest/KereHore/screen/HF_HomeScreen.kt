package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_CircleButton
import com.hackfest.KereHore.widgets.HF_PocketCard
import com.hackfest.KereHore.widgets.HF_PopUpCard
import com.hackfest.KereHore.widgets.HF_TextField
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HF_HomeScreen(
    navController: androidx.navigation.NavController,

    pocket:
    //List<HF_DummyTemplate> = HF_GetDummies(), //for testing
    List<HF_PocketObject>,                      //for real usage

    totalBalance: List<HF_TotalBalanceObject>,

    addPocketObject   : (HF_PocketObject) -> Unit,
    removePocketObject: (UUID) -> Unit,
    deleteAllPocket   : (HF_PocketObject) -> Unit,

    addTotalBalance   : (HF_TotalBalanceObject) -> Unit,
    deleteAllBalance  : (HF_TotalBalanceObject) -> Unit
){
    var displayForm = remember {
        mutableStateOf(false)
    }
    var displayDebug = remember {
        mutableStateOf(false)
    }
    var addPocketFlag = remember {
        mutableStateOf(false)
    }
    var addTotalBalanceFlag = remember {
        mutableStateOf(false)
    }
    var newPocketTitle = remember {
        mutableStateOf("")
    }
    var newTotalBalance = remember {
        mutableStateOf("")
    }
    var exceptionString = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current as Activity


    var currentTotalBalance: String
    var currentAvailableBalance: String
    var currentAssignedBalance: String = "0.0"
    var collectAssignedBalance: Double = 0.0

    if(totalBalance.size == 0){
        currentTotalBalance = "0.0"
        currentAvailableBalance = "0.0"
    } else {
        currentTotalBalance = totalBalance[totalBalance.size - 1].totalBalance
        currentAvailableBalance = totalBalance[totalBalance.size - 1].balanceAvailableToAlocate

        for(i in 0..pocket.size - 1){
            collectAssignedBalance += pocket[i].pocketBalance.toDouble()
        }
        currentAssignedBalance = collectAssignedBalance.toString()
    }

    currentAvailableBalance = (currentTotalBalance.toDouble() - currentAssignedBalance.toDouble()).toString()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.onPrimary) {
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
                        Text(
                            text = "FUNCTIONAL TEST",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(500),
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
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
                ) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 15.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier
                                .fillMaxHeight()
                                .width(250.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
                                Text(text = "Rp.$currentTotalBalance", fontSize = 25.sp, fontWeight = FontWeight(500))
                                if (!currentTotalBalance.equals("0.0")){
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(text = "Available to\nalocate: Rp.$currentAvailableBalance", lineHeight = 18.sp)
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(100.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                if(addPocketFlag.value == false && currentTotalBalance.equals("0.0")){
                                    HF_CircleButton(
                                        imageVector = Icons.Default.AddCircle
                                    ) {trigger ->
                                        if(trigger.isNotEmpty()){
                                            displayForm.value         = !displayForm.value
                                            addTotalBalanceFlag.value = !addTotalBalanceFlag.value
                                        }
                                    }
                                }

                                if(addPocketFlag.value == false && !currentTotalBalance.equals("0.0")){
                                    HF_CircleButton(
                                        imageVector = Icons.Default.Create
                                    ) {trigger ->
                                        if(trigger.isNotEmpty()){
                                            displayForm.value         = !displayForm.value
                                            addTotalBalanceFlag.value = !addTotalBalanceFlag.value
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(pocket.size > 0){
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns  = GridCells.Fixed(2)
                        ){
                            items(pocket){ pocket ->
                                HF_PocketCard(pocket = pocket){pocketID ->
                                    navController.navigate(route = HF_NavEnum.HF_PocketScreen.name+"/$pocketID")
                                }
                            }
                        }
                    }
                }
            },

            bottomBar = {
                if (displayForm.value == false && displayDebug.value == false) {
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
                        ) {
                            HF_CircleButton(imageVector = Icons.Default.Star) { trigger ->
                                if(trigger.isNotEmpty()){
                                    exceptionString.value = "under development"
                                }
                            }
                            HF_CircleButton(imageVector = Icons.Default.Home) {trigger ->
                                if(trigger.isNotEmpty()){
                                    navController.navigate(route = HF_NavEnum.HF_NEW_HomeScreen.name)
                                }
                            }
                            HF_CircleButton(imageVector = Icons.Default.AccountCircle){trigger ->
                                if(trigger.isNotEmpty()){
                                    exceptionString.value = "under development"
                                }
                            }
                        }
                    }

                } else if(displayDebug.value == true) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(text = "For testing purposes", fontSize = 20.sp)
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "DeleteAllPocket")
                                HF_CircleButton(imageVector = Icons.Default.Delete){trigger ->
                                    if(trigger.isNotEmpty()){
                                        deleteAllPocket(HF_PocketObject())
                                        context.recreate()
                                    }
                                }
                            }

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "DeleteTotalBalance")
                                HF_CircleButton(imageVector = Icons.Default.Delete){trigger ->
                                    if(trigger.isNotEmpty()){
                                        deleteAllBalance(HF_TotalBalanceObject())
                                        context.recreate()
                                    }
                                }
                            }

                            HF_CircleButton(imageVector = Icons.Default.Check){trigger ->
                                if(trigger.isNotEmpty()){
                                    displayDebug.value = !displayDebug.value
                                }
                            }
                        }
                    }

                } else if (displayForm.value == true){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                text = if(addPocketFlag.value == true){
                                    "Add New Pocket"
                                } else {
                                    "Insert total balance"
                                },
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_TextField(
                                keyboardType =
                                if (addPocketFlag.value == true){
                                    KeyboardType.Text
                                } else {
                                    KeyboardType.Number
                                },
                                text  =
                                if(addPocketFlag.value == true){
                                   newPocketTitle.value

                                } else {
                                    newTotalBalance.value

                                },
                                label = "Textfield",
                                onTextChange = {
                                if ((it.all { char -> char.isDigit() }) && addTotalBalanceFlag.value == true){
                                    newTotalBalance.value = it

                                } else if ((it.all { char -> char.isDefined() || char.isWhitespace()}) &&addPocketFlag.value == true){
                                    newPocketTitle.value = it

                                }
                            })
                            Spacer(modifier = Modifier.padding(10.dp))
                            HF_CircleButton(imageVector = Icons.Default.Check){trigger ->
                                if (trigger.isNotEmpty()) {
                                    if (addPocketFlag.value == true) {
                                        addPocketObject(HF_PocketObject(pocketTitle = newPocketTitle.value))
                                        newPocketTitle.value = ""
                                        addPocketFlag.value = !addPocketFlag.value

                                    } else if (addTotalBalanceFlag.value == true) {
                                        if(newTotalBalance.value.toDouble() >= currentAssignedBalance.toDouble()) {
                                            addTotalBalance(HF_TotalBalanceObject(totalBalance = newTotalBalance.value))
                                            newTotalBalance.value = ""
                                            addTotalBalanceFlag.value = !addTotalBalanceFlag.value
                                        } else {
                                            newTotalBalance.value = ""
                                            addTotalBalanceFlag.value = !addTotalBalanceFlag.value
                                            exceptionString.value = "invalid balance"
                                        }
                                    }

                                    displayForm.value = !displayForm.value
                                }
                            }
                        }
                    }
                }
            },

            floatingActionButton = ({
                if(addTotalBalanceFlag.value == false && !currentTotalBalance.equals("0.0")){
                    HF_CircleButton() { trigger ->
                        if(trigger.isNotEmpty()){
                            displayForm.value = !displayForm.value
                            addPocketFlag.value = !addPocketFlag.value
                        }
                    }
                }
            }),

            snackbarHost = ({
                if(!exceptionString.value.equals("")){
                    HF_PopUpCard(
                        text = when(exceptionString.value){
                            "invalid balance" -> "New total balance can't be smaller than current assigned balance"
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