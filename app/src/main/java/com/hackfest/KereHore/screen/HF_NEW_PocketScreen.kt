package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hackfest.KereHore.R
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_BarButton
import com.hackfest.KereHore.widgets.HF_CircleButton
import com.hackfest.KereHore.widgets.HF_NEW_PocketScreenInput
import com.hackfest.KereHore.widgets.HF_TextField
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun HF_NEW_PocketScreen(
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
    val current   = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MMM dd (hh:mm)")
    val formatted = current.format(formatter)

    val pocket  = pocketList.filter { pocket -> pocket.pocketID.toString().equals(pocketID) }
    val context = LocalContext.current //as Activity

    var displayInput = remember {
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
    var preventFlag = remember {
        mutableStateOf(false)
    }
    var clickFlag = remember {
        mutableStateOf("1")
    }
    var historyDescription = remember {
        mutableStateOf("")
    }
    var displayDelete = remember {
        mutableStateOf(false)
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
        listOfPocketHistory = listOfPocketHistory.reversed()
    } else {
        listOfPocketHistory = listOf()
    }

    var color: Color = MaterialTheme.colorScheme.primary
    var icon: Painter = painterResource(id = R.drawable.gdsclogo)
    var background: Painter = painterResource(id = R.drawable.google)
    var easterEgg = false

    //pocket.pocketTitle = "pluto"
    when(pocket[0].pocketTitle.get(0)){
        'A','B','C','D','E','a','b','c','d','e' -> color = Color(170, 70, 80)
        'F','G','H','I','J','f','g','h','i','j' -> color = Color(70, 170, 155)
        'K','L','M','N','O','k','l','m','n','o' -> color = Color(70, 95, 170)
        'P','Q','R','S','T','p','q','r','s','t' -> color = Color(70, 170, 100)
        'U','V','W','X','Y','u','v','w','x','y' -> color = Color(100, 70, 170)
        'Z','z'                                 -> color = Color(101, 100, 102)
    }
    when(pocket[0].pocketTitle){
        "HACKFEST","Hackfest","HackFest","hackfest"   -> {
            icon = painterResource(id = R.drawable.gdsclogo)
            easterEgg = true
            background = painterResource(id = R.drawable.google)
            color = Color(100, 70, 170)
        }
        "FILKOM","Filkom","filkom"                     -> {
            icon = painterResource(id = R.drawable.logo_filkom)
            easterEgg = true
            background = painterResource(id = R.drawable.filkom)
            color = Color(70, 170, 100)
        }
        "SUPER SQUARE", "Super Square", "super square", "Super square"  -> {
            icon = painterResource(id = R.drawable.supersquare)
            easterEgg = true
            background = painterResource(id = R.drawable.supermario)
        }
        "PLUTO","Pluto","pluto"                        -> {
            icon = painterResource(id = R.drawable.pluto)
            easterEgg = true
            background = painterResource(id = R.drawable.stars)
            color = Color(101, 100, 102)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = color) {
        Scaffold(
            content = {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f), color = color
                ) {
                    if(easterEgg == true){
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .paint(
                                background,
                                contentScale = ContentScale.Crop
                            )
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp)
                                    .padding(bottom = 27.5.dp, end = 10.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Spacer(modifier = Modifier.padding(1.dp))
                                HF_CircleButton(imageVector = Icons.Default.ArrowBack, color = color){trigger ->
                                    if(trigger.isNotEmpty()){
                                        navController.navigate(route = HF_NavEnum.HF_NEW_PocketPage.name)
                                    }
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                                .padding(bottom = 30.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(bottom = 6.dp)
                                    .clickable { navController.navigate(route = HF_NavEnum.HF_NEW_PocketPage.name) },
                                contentDescription = "")
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(text = "${pocket[0].pocketTitle}", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.883f),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Scaffold(
                        content = {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                                .padding(top = 22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(text = "Your Pocket Balance", fontSize = 26.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.inverseSurface)
                                Spacer(modifier = Modifier.padding(10.dp))

                                if(displayInput.value == true){
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    HF_NEW_PocketScreenInput(){ returnValue ->
                                        clickFlag.value = returnValue
                                    }

                                    Spacer(modifier = Modifier.padding(8.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                                        elevation = CardDefaults.cardElevation(5.dp),
                                    ){
                                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                            HF_TextField(
                                                keyboardType = KeyboardType.Number,
                                                text = newReportedSpending.value, label = "Modify pocket balance",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDigit()} )){
                                                        newReportedSpending.value = it
                                                        preventFlag.value = false
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(5.dp))

                                            HF_TextField(
                                                keyboardType = KeyboardType.Text,
                                                text = historyDescription.value, label = "Description",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() } )){
                                                        historyDescription.value = it
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(3.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    if(newReportedSpending.value.equals("")){
                                        preventFlag.value = true
                                    }
                                    HF_BarButton(text = "Apply changes",
                                        color =
                                        if(preventFlag.value == false){
                                            Color(70, 95, 170)
                                        } else {
                                            Color(101, 100, 102)
                                        }
                                    ) {trigger ->
                                        var historyNotes: String = ""
                                        when(clickFlag.value){
                                            "1" -> {
                                                pocket[0].pocketBalance = newReportedSpending.value
                                                historyNotes = "(Alocate: ${historyDescription.value})"
                                            }
                                            "2" -> {
                                                pocket[0].pocketBalance = (pocket[0].pocketBalance.toDouble() + newReportedSpending.value.toDouble()).toString()
                                                currentTotalBalance =
                                                    (currentTotalBalance.toDouble() + newReportedSpending.value.toDouble()).toString()
                                                historyNotes = "(Income: ${historyDescription.value})"
                                            }
                                            "3" -> {
                                                pocket[0].pocketBalance = (pocket[0].pocketBalance.toDouble() - newReportedSpending.value.toDouble()).toString()
                                                currentTotalBalance =
                                                    (currentTotalBalance.toDouble() - newReportedSpending.value.toDouble()).toString()
                                                historyNotes = "(Expense: ${historyDescription.value})"
                                            }
                                        }
                                        if(preventFlag.value == false){
                                            if(trigger.isNotEmpty() && (newReportedSpending.value.toDouble() <= pocket[0].pocketBalance.toDouble())){
                                                newReportedSpending.value = "$formatted|Rp.${newReportedSpending.value} $historyNotes"
                                                reportSpending(
                                                    Triple(
                                                        pocket[0].pocketID,
                                                        newReportedSpending.value,
                                                        pocket[0].pocketBalance
                                                    )
                                                )

                                                addTotalBalance(HF_TotalBalanceObject(totalBalance = currentTotalBalance))

                                                newReportedSpending.value = ""
                                                historyDescription.value = ""
                                                displayInput.value = !displayInput.value
                                                navController.navigate(route = HF_NavEnum.HF_NEW_PocketScreen.name + "/${pocket[0].pocketID.toString()}")
                                            } else {
                                                newPocketBalance.value = ""
                                                displayInput.value = !displayInput.value
                                                exceptionString.value = "invalid balance"
                                            }
                                        }
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier
                                            //.fillMaxSize()
                                    ) {
                                        Card(modifier = Modifier
                                            .fillMaxWidth()
                                            .height(130.dp),
                                            colors = CardDefaults.cardColors(color),
                                            shape = RoundedCornerShape(20.dp),
                                            elevation = CardDefaults.cardElevation(8.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(15.dp)
                                                    .padding(start = 16.dp),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Top
                                            ) {
                                                Text(
                                                    text = "Rp.${pocket[0].pocketBalance}",
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White
                                                )
                                                Spacer(modifier = Modifier.padding(1.dp))
                                                Text(
                                                    text = "Description: ${pocket[0].pocketDescription}",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White
                                                )
                                            }
                                        }

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(290.dp)
                                                .padding(
                                                    //start = 25.dp,
                                                    //end = 25.dp,
                                                    top = 15.dp,
                                                    //bottom = 5.dp
                                                ),
                                            shape = RoundedCornerShape(15.dp),
                                            elevation = CardDefaults.cardElevation(8.dp),
                                            //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(40.dp),
                                                    shape = RoundedCornerShape(
                                                        topStart = 15.dp,
                                                        topEnd = 15.dp
                                                    ),
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            101,
                                                            100,
                                                            102
                                                        )
                                                    )
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(start = 15.dp, end = 15.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text(
                                                            text = "History",
                                                            fontSize = 18.sp,
                                                            fontWeight = FontWeight(500),
                                                            color = Color.White
                                                        )

                                                        Row(verticalAlignment = Alignment.CenterVertically) {

                                                            Spacer(modifier = Modifier.padding(3.dp))
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.history),
                                                                contentDescription = "History",
                                                                modifier = Modifier.fillMaxHeight(
                                                                    0.6f
                                                                ),
                                                                tint = Color.White
                                                            )
                                                        }
                                                    }
                                                }
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(
                                                            top = 10.dp,
                                                            start = 15.dp,
                                                            end = 15.dp,
                                                            bottom = 15.dp
                                                        )
                                                ) {
                                                    items(listOfPocketHistory) { pocketHistory ->
                                                        var splitPocketHistory =
                                                            pocketHistory.split("|")
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(35.dp),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            Row(
                                                                modifier = Modifier.fillMaxSize(),
                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Text(
                                                                    text = "${splitPocketHistory[1]}",
                                                                    fontSize = 12.sp
                                                                )
                                                                Text(
                                                                    text = "${splitPocketHistory[0]}",
                                                                    fontSize = 12.sp
                                                                )
                                                            }
                                                        }
                                                        Divider(
                                                            color = Color(101, 100, 102),
                                                            thickness = 1.dp
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(displayDelete.value == true){
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        HF_BarButton(text = "Delete this pocket", color = Color(170, 70, 80)){trigger ->
                                            if(trigger.isNotEmpty()){
                                                deleteThisPocket(pocket[0].pocketID)
                                                navController.navigate(route = HF_NavEnum.HF_NEW_HomeScreen.name)
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(85.dp)
                                .padding(start = 15.dp, end = 15.dp)) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(65.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors = CardDefaults.cardColors(color)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(17.dp)
                                            .clickable { Toast.makeText(context, "Coming soon...🚧🏗️", Toast.LENGTH_LONG).show() }
                                            , contentAlignment = Alignment.Center){
                                            Icon(painter = painterResource(id = R.drawable.statistics), contentDescription = "home", tint = Color.White)
                                        }
                                        Card(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp)
                                                .clickable {
                                                    displayInput.value = !displayInput.value
                                                },
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(Color.White),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                        ) {
                                            Box(modifier = Modifier
                                                .fillMaxSize()
                                                .padding(
                                                    if (easterEgg == true) {
                                                        8.dp
                                                    } else {
                                                        15.dp
                                                    }
                                                ),
                                                contentAlignment = Alignment.Center){
                                                if(easterEgg == true){
                                                    Image(painter = icon, contentDescription = "")
                                                } else {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.finance),
                                                        contentDescription = "home",
                                                        tint = color
                                                    )
                                                }
                                            }
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(16.dp)
                                            .padding(top = 3.dp)
                                            .clickable {
                                                displayDelete.value = !displayDelete.value
                                            }
                                            , contentAlignment = Alignment.Center){
                                            Icon(painter = painterResource(id = R.drawable.list), contentDescription = "pocket", tint = Color.White)
                                        }
                                    }
                                }
                            }

                        }
                    )
                }
            },
        )
    }
}