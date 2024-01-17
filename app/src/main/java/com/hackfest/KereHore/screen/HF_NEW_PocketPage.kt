package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackfest.KereHore.R
import com.hackfest.KereHore.database.HF_DummyTemplate
import com.hackfest.KereHore.database.HF_GetDummies
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_BarButton
import com.hackfest.KereHore.widgets.HF_CircleButton
import com.hackfest.KereHore.widgets.HF_NEW_HomeScreenTiles
import com.hackfest.KereHore.widgets.HF_NEW_PocketCard
import com.hackfest.KereHore.widgets.HF_PocketCard
import com.hackfest.KereHore.widgets.HF_TextField
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HF_NEW_PocketPage(
    navController: androidx.navigation.NavController,

    pocket:
    //List<HF_DummyTemplate> = HF_GetDummies(), //for testing
    List<HF_PocketObject>,                      //for real usage
    totalBalance: List<HF_TotalBalanceObject>,
    addPocketObject   : (HF_PocketObject) -> Unit,
    removePocketObject: (UUID) -> Unit,
    deleteAllPocket   : (HF_PocketObject) -> Unit,
){
    var currentTotalBalance: String
    if(totalBalance.size == 0){
        currentTotalBalance = "0.0"
    } else {
        currentTotalBalance = totalBalance[totalBalance.size - 1].totalBalance
    }

    var header = ""
    if (currentTotalBalance.toDouble() == 0.0){
        header = "We have no money😔"
    } else if (currentTotalBalance.toDouble() > 0.0 && currentTotalBalance.toDouble() <= 150000.0){
        header = "Ain't much, but no problem😅"
    } else if (currentTotalBalance.toDouble() > 150000.0 && currentTotalBalance.toDouble() <= 400000.0){
        header = "Enough for a few days👍"
    } else if (currentTotalBalance.toDouble() > 400000.0 && currentTotalBalance.toDouble() <= 700000.0){
        header = "Everything seems normal👌"
    } else if (currentTotalBalance.toDouble() > 700000.0 && currentTotalBalance.toDouble() <= 900000.0){
        header = "How about self reward?😁"
    } else if (currentTotalBalance.toDouble() > 900000.0 && currentTotalBalance.toDouble() <= 999999.0){
        header = "Become a millionaire soon🤩"
    } else if (currentTotalBalance.toDouble() >= 1000000.0){
        header = "We're so rich fr...🥵"
    }

    if(((currentTotalBalance.toDouble() / 69.0) % 10.0 == 0.0) && currentTotalBalance.toDouble() != 0.0){
        header = "Nice😼"
    }
    if(((currentTotalBalance.toDouble() / 314.0) % 10.0 == 0.0) && currentTotalBalance.toDouble() != 0.0){
        header = "3.14159265359...🧮"
    }

    var displayInput = remember {
        mutableStateOf(false)
    }
    var newPocketTitle = remember {
        mutableStateOf("")
    }
    var newPocketDescription = remember {
        mutableStateOf("")
    }
    var preventFlag = remember {
        mutableStateOf(false)
    }

    val context   = LocalContext.current
    val current   = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy")
    val formatted = current.format(formatter)

    Surface(modifier = Modifier.fillMaxSize(), color = Color(70, 95, 170)) {
        Scaffold(
            content = {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f), color = Color(70, 95, 170)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .padding(bottom = 30.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(text = "$formatted", color = Color.White, fontSize = 15.sp)
                            Spacer(modifier = Modifier.padding(1.dp))
                            Text(text = header, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        HF_CircleButton(imageVector = Icons.Default.AccountCircle){ trigger ->
                            if(trigger.isNotEmpty()){
                                Toast.makeText(context, "Coming soon...🚧🏗️", Toast.LENGTH_LONG).show()
                            }
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
                                .fillMaxWidth()
                                .fillMaxHeight(0.92f)
                                .padding(15.dp)
                                .padding(top = 22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(text = "Your Pocket", fontSize = 30.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.inverseSurface)
                                Spacer(modifier = Modifier.padding(5.dp))
                                if (displayInput.value == true){
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
                                                keyboardType = KeyboardType.Text,
                                                text = newPocketTitle.value, label = "Insert Pocket Title",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() } )){
                                                        newPocketTitle.value = it
                                                        preventFlag.value = false
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(5.dp))
                                            HF_TextField(
                                                keyboardType = KeyboardType.Text,
                                                text = newPocketDescription.value, label = "Insert Pocket Description",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() } )){
                                                        newPocketDescription.value = it
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(3.dp))
                                            if(newPocketTitle.value.equals("")){
                                                preventFlag.value = true
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.padding(8.dp))

                                    HF_BarButton(text = "Apply changes",
                                        color =
                                        if(preventFlag.value == false){
                                            Color(70, 95, 170)
                                        } else {
                                            Color(101, 100, 102)
                                        }
                                    ){
                                        if(preventFlag.value == false){
                                            addPocketObject(HF_PocketObject(pocketTitle = newPocketTitle.value, pocketDescription = newPocketDescription.value))
                                            newPocketTitle.value = ""
                                            newPocketDescription.value = ""
                                            displayInput.value = !displayInput.value
                                        }
                                    }
                                } else {
                                    if(pocket.size < 1){
                                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                                            Image(painter = painterResource(id = R.drawable.cat), contentDescription = "cat", modifier = Modifier.fillMaxSize(0.4f))
                                            Text(text = "You have no pocket, Sir :)", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.inverseSurface)
                                            Spacer(modifier = Modifier.padding(85.dp))
                                        }
                                    } else {
                                        LazyVerticalGrid(
                                            modifier = Modifier.fillMaxSize(),
                                            columns  = GridCells.Fixed(2),
                                            verticalArrangement = Arrangement.spacedBy(2.dp),
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ){
                                            items(pocket){ pocket ->
                                                HF_NEW_PocketCard(pocket = pocket){pocketID ->
                                                    navController.navigate(route = HF_NavEnum.HF_NEW_PocketScreen.name+"/$pocketID")
                                                }
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
                                    colors = CardDefaults.cardColors(Color(200, 210, 230))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(15.dp), contentAlignment = Alignment.Center){
                                            Icon(
                                                painter = painterResource(id = R.drawable.history),
                                                contentDescription = "home", tint = Color(70, 95, 170),
                                                modifier = Modifier.clickable { navController.navigate(route = HF_NavEnum.HF_NEW_HistoryScreen.name) }
                                            )
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(17.dp), contentAlignment = Alignment.Center){
                                            Icon(
                                                painter = painterResource(id = R.drawable.home),
                                                contentDescription = "pocket",
                                                tint = Color(70, 95, 170),
                                                modifier = Modifier.clickable { navController.navigate(route = HF_NavEnum.HF_NEW_HomeScreen.name) }
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp),
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(Color(70, 95, 170)),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                            border = BorderStroke(3.dp, Color.White)
                                        ) {
                                            Box(modifier = Modifier
                                                .fillMaxSize()
                                                .padding(15.dp)
                                                .padding(top = 2.dp), contentAlignment = Alignment.Center){
                                                Icon(painter = painterResource(id = R.drawable.pocket), contentDescription = "home", tint = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        floatingActionButton = {
                            Card(
                                modifier = Modifier
                                    .width(65.dp)
                                    .height(65.dp)
                                    .clickable { displayInput.value = !displayInput.value },
                                shape    = CircleShape,
                                colors   = CardDefaults.cardColors(Color(70, 95, 170)),
                                elevation = CardDefaults.cardElevation(10.dp),
                                border = BorderStroke(3.dp, color = Color.White)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp)
                                    .padding(top = 2.dp), contentAlignment = Alignment.Center){
                                    Icon(painter = painterResource(id = R.drawable.sign), contentDescription = "new pocket", tint = Color.White)
                                }
                            }
                        }
                    )
                }
            },
        )
    }
}