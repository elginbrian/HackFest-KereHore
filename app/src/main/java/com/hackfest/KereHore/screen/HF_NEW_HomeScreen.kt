package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackfest.KereHore.R
import com.hackfest.KereHore.model.HF_PocketObject
import com.hackfest.KereHore.model.HF_TotalBalanceObject
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_BarButton
import com.hackfest.KereHore.widgets.HF_CircleButton
import com.hackfest.KereHore.widgets.HF_NEW_HomeScreenInput
import com.hackfest.KereHore.widgets.HF_NEW_HomeScreenTiles
import com.hackfest.KereHore.widgets.HF_TextField
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun HF_NEW_HomeScreen(
    navController: androidx.navigation.NavController,

    pocket:
    //List<HF_DummyTemplate> = HF_GetDummies(), //for testing
    List<HF_PocketObject>,                      //for real usage

    totalBalance: List<HF_TotalBalanceObject>,

    addTotalBalance   : (HF_TotalBalanceObject) -> Unit,
    deleteAllBalance  : (HF_TotalBalanceObject) -> Unit
){
    var displayInput = remember {
        mutableStateOf(false)
    }
    var newTotalBalance = remember {
        mutableStateOf("")
    }
    var exceptionString = remember {
        mutableStateOf("")
    }
    var clickflag = remember {
        mutableStateOf("1")
    }
    var preventFlag = remember {
        mutableStateOf(false)
    }

    var currentTotalBalance: String
    var currentAvailableBalance: String
    var currentAssignedBalance: String = "0.0"
    var collectAssignedBalance: Double = 0.0
    var currentIncomeBalance: String = "0.0"
    var currentExpenseBalance: String = "0.0"

    if(pocket.size > 0){
        for(i in 0..pocket.size - 1){
            var listOfPocketHistory = pocket[i].pocketHistory.split("-")

            if(listOfPocketHistory.size > 1){
                listOfPocketHistory = listOfPocketHistory.subList(1, listOfPocketHistory.size)

                for(j in 0..listOfPocketHistory.size - 1){
                    var splitContent = listOfPocketHistory[j].split("|Rp.")
                    var splitContent2 = splitContent[1].split(" ")

                    if(splitContent2[1].contains("Income")){
                        currentIncomeBalance = (currentIncomeBalance.toDouble() + splitContent2[0].toDouble()).toString()

                    } else if (splitContent2[1].contains("Expense")){
                        currentExpenseBalance = (currentExpenseBalance.toDouble() + splitContent2[0].toDouble()).toString()

                    }
                }
            }
        }
    }

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

    val current   = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy")
    val formatted = current.format(formatter)
    val context = LocalContext.current

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
                                .fillMaxSize()
                                .padding(15.dp)
                                .padding(top = 22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(text = "Your Balance", fontSize = 30.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.inverseSurface)
                                Spacer(modifier = Modifier.padding(10.dp))
                                if(displayInput.value == false){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(145.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        HF_NEW_HomeScreenTiles(
                                            title = "Total Balance",
                                            balance = currentTotalBalance,
                                            color = Color(100, 70, 170),
                                            painter = painterResource(id = R.drawable.all),
                                            tileInfo = "Basically, all of your money."
                                        )

                                        HF_NEW_HomeScreenTiles(
                                            title = "Not Alocated",
                                            balance = currentAvailableBalance,
                                            color = Color(101, 100, 102),
                                            painter = painterResource(id = R.drawable.question),
                                            tileInfo = "Money which aren't assigned to a pocket"
                                        )

                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ){
                                        HF_NEW_HomeScreenTiles(
                                            title = "Income",
                                            balance = currentIncomeBalance,
                                            color = Color(70, 170, 100),
                                            painter = painterResource(id = R.drawable.profit),
                                            tileInfo = "Incomes recorded through pocket history"
                                        )

                                        HF_NEW_HomeScreenTiles(
                                            title = "Expendature",
                                            balance = currentExpenseBalance,
                                            color = Color(170, 70, 80),
                                            painter = painterResource(id = R.drawable.loss),
                                            tileInfo = "Expenses recorded through pocket history"
                                        )
                                    }
                                    HF_BarButton(text = "Modify total balance", color = Color(70, 95, 170)){trigger ->
                                        if(trigger.isNotEmpty()){
                                            displayInput.value = !displayInput.value
                                        }

                                    }

                                } else {
                                    HF_NEW_HomeScreenInput(
                                        title = "Total Balance",
                                        balance = currentTotalBalance,
                                        painter = painterResource(id = R.drawable.all)
                                    ){returnValue ->
                                        clickflag.value = returnValue
                                    }
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(85.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                                        elevation = CardDefaults.cardElevation(5.dp),
                                    ){
                                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                            HF_TextField(
                                                keyboardType = KeyboardType.Number,
                                                text = newTotalBalance.value, label = "Modify total balance",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDigit()} )){
                                                        newTotalBalance.value = it
                                                        preventFlag.value = false
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(3.dp))
                                        }
                                    }
                                    if(newTotalBalance.value.equals("")){
                                        preventFlag.value = true
                                    }
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    HF_BarButton(text = "Apply changes",
                                        color =
                                            if(preventFlag.value == false){
                                                Color(70, 95, 170)
                                            } else {
                                                Color(101, 100, 102)
                                            }
                                    ) {trigger ->
                                        when(clickflag.value){
                                            "1" -> newTotalBalance.value = newTotalBalance.value
                                            "2" -> newTotalBalance.value = (currentTotalBalance.toDouble() + newTotalBalance.value.toDouble()).toString()
                                            "3" -> newTotalBalance.value = (currentTotalBalance.toDouble() - newTotalBalance.value.toDouble()).toString()
                                        }
                                        if(preventFlag.value == false){
                                            if(trigger.isNotEmpty() && (newTotalBalance.value.toDouble() >= currentAssignedBalance.toDouble())){
                                                addTotalBalance(HF_TotalBalanceObject(totalBalance = newTotalBalance.value))
                                                newTotalBalance.value = ""
                                                displayInput.value = !displayInput.value
                                            } else {
                                                newTotalBalance.value = ""
                                                Toast.makeText(context, "New total balance can't be smaller than current assigned balance.", Toast.LENGTH_LONG).show()
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
                                                .padding(15.dp), contentAlignment = Alignment.Center){
                                                Icon(painter = painterResource(id = R.drawable.home), contentDescription = "home", tint = Color.White)
                                            }
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(16.dp)
                                            .padding(top = 3.dp), contentAlignment = Alignment.Center){
                                            Icon(
                                                painter = painterResource(id = R.drawable.pocket),
                                                contentDescription = "pocket", tint = Color(70, 95, 170),
                                                modifier = Modifier.clickable { navController.navigate(route = HF_NavEnum.HF_NEW_PocketPage.name) }
                                            )
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
