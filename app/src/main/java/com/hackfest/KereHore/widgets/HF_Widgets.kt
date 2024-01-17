package com.hackfest.KereHore.widgets

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackfest.KereHore.R
import com.hackfest.KereHore.model.HF_PocketObject

//@Preview
@Composable
fun HF_PocketCard(
    pocket:
    //HF_DummyTemplate = HF_GetDummies()[0],
    HF_PocketObject,
    whenWidgetClicked: (String) -> Unit = {}){

    Card(modifier = Modifier
        .padding(10.dp)
        .height(150.dp)
        .width(150.dp)
        .clickable { whenWidgetClicked(pocket.pocketID.toString()) }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = "Rp.${pocket.pocketBalance}", fontSize = 20.sp, fontWeight = FontWeight(500))
            Text(text = pocket.pocketTitle)
        }
    }
}

//@Preview
@Composable
fun HF_CircleButton(
    imageVector: ImageVector = Icons.Default.Add,
    color: Color = MaterialTheme.colorScheme.primary,
    whenWidgetClicked: (String) -> Unit = {}
){
    val trigger = "trigger"
    Card(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .clickable { whenWidgetClicked(trigger) },
        shape = CircleShape,
        colors = CardDefaults.cardColors(color)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = imageVector, contentDescription = "Add Pocket", tint = Color.White)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HF_TextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
){
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.primary),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction
            keyboardController?.hide()
        }),

        modifier = Modifier
    )
}

//@Preview
@Composable
fun HF_PopUpCard(
    text: String = "Lorem Ipsum",
    modifier: Modifier = Modifier,
    trigger: (String) -> Unit = {}
){
    Column(modifier = Modifier
        .width(220.dp)
        .height(280.dp)) {
        Card(modifier = Modifier
            .width(220.dp)
            .height(180.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, bottom = 20.dp, start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = text, fontSize = 16.sp, fontWeight = FontWeight(500), textAlign = TextAlign.Center)
                HF_CircleButton(imageVector = Icons.Default.Check) {trigger ->
                    if(trigger.isNotEmpty()){
                        trigger("trigger")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(100.dp))
    }
}

//@Preview
@Composable
fun HF_BarButton(
    text: String = "Lorem Ipsum",
    color: Color = MaterialTheme.colorScheme.primary,
    whenWidgetClicked: (String) -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { whenWidgetClicked("trigger") },
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

//@Preview
@Composable
fun HF_NEW_HomeScreenTiles(
    title: String = "Lorem Ipsum",
    balance: String = "25000.0",
    painter: Painter = painterResource(id = R.drawable.all),
    tileInfo: String = "Lorem Ipsum",
    color: Color = MaterialTheme.colorScheme.primary
){
    val tilesClicked = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .width(175.dp)
            .height(130.dp)
            .clickable { tilesClicked.value = !tilesClicked.value },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        if(tilesClicked.value == false){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(balance.toDouble() < 10000.0){
                    Card(modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painter = painter, contentDescription = "", modifier = Modifier
                                .width(30.dp)
                                .height(30.dp), tint = color)
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Rp.$balance", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Card(modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(painter = painter, contentDescription = "", modifier = Modifier
                                    .width(35.dp)
                                    .height(35.dp), tint = color)
                            }
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Rp.$balance", fontSize = 23.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(start = 5.dp, end = 5.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text = tileInfo, color = Color.White, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}

//@Preview
@Composable
fun HF_NEW_PocketCard(
    pocket:
    //HF_DummyTemplate = HF_GetDummies()[0],
    HF_PocketObject,
    whenWidgetClicked: (String) -> Unit = {}){
    var color: Color = MaterialTheme.colorScheme.primary
    var icon: Painter = painterResource(id = R.drawable.gdsclogo)
    var easterEgg = false

    when(pocket.pocketTitle.get(0)){
        'A','B','C','D','E','a','b','c','d','e' -> color = Color(170, 70, 80)
        'F','G','H','I','J','f','g','h','i','j' -> color = Color(70, 170, 155)
        'K','L','M','N','O','k','l','m','n','o' -> color = Color(70, 95, 170)
        'P','Q','R','S','T','p','q','r','s','t' -> color = Color(70, 170, 100)
        'U','V','W','X','Y','u','v','w','x','y' -> color = Color(100, 70, 170)
        'Z','z'                                 -> color = Color.Unspecified
    }
    when(pocket.pocketTitle){
        "HACKFEST","Hackfest","HackFest","hackfest"   -> {
            icon = painterResource(id = R.drawable.gdsclogo)
            easterEgg = true
        }
        "FILKOM","Filkom","filkom"                     -> {
            icon = painterResource(id = R.drawable.logo_filkom)
            easterEgg = true
        }
        "SUPER SQUARE", "Super Square", "super square", "Super square"  -> {
            icon = painterResource(id = R.drawable.supersquare)
            easterEgg = true
        }
        "PLUTO","Pluto","pluto"                        -> {
            icon = painterResource(id = R.drawable.pluto)
            easterEgg = true
        }
    }

    Card(modifier = Modifier
        .padding(10.dp)
        .height(145.dp)
        .width(160.dp)
        .clickable { whenWidgetClicked(pocket.pocketID.toString()) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            if(easterEgg == true){
                MaterialTheme.colorScheme.onPrimary
            } else {
                color
            }
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            if(easterEgg == true){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(painter = icon, contentDescription = "", modifier = Modifier
                        .width(100.dp)
                        .height(70.dp),

                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Rp.${pocket.pocketBalance}", fontSize = 20.sp, fontWeight = FontWeight(500), color = MaterialTheme.colorScheme.inverseSurface)
                Text(text = pocket.pocketTitle, color = MaterialTheme.colorScheme.inverseSurface)
            } else {
                Text(text = "Rp.${pocket.pocketBalance}", fontSize = 20.sp, fontWeight = FontWeight(500), color = Color.White)
                Text(text = pocket.pocketTitle, color = Color.White)
            }
        }
    }
}

//@Preview
@Composable
fun HF_NEW_HistoryCard(
    pocket: HF_PocketObject
){
    var color: Color = MaterialTheme.colorScheme.primary

    var listOfPocketHistory = pocket.pocketHistory.split("-")
    var extended = remember {
        if(listOfPocketHistory.size > 1){
            mutableStateOf(true)
        } else {
            mutableStateOf(false)
        }
    }
    if(listOfPocketHistory.size > 1){
        listOfPocketHistory = listOfPocketHistory.subList(1, listOfPocketHistory.size)
        listOfPocketHistory = listOfPocketHistory.reversed()
    } else {
        listOfPocketHistory = listOf()
    }
    when(pocket.pocketTitle.get(0)){
        'A','B','C','D','E','a','b','c','d','e' -> color = Color(170, 70, 80)
        'F','G','H','I','J','f','g','h','i','j' -> color = Color(70, 170, 155)
        'K','L','M','N','O','k','l','m','n','o' -> color = Color(70, 95, 170)
        'P','Q','R','S','T','p','q','r','s','t' -> color = Color(70, 170, 100)
        'U','V','W','X','Y','u','v','w','x','y' -> color = Color(100, 70, 170)
        'Z','z'                                 -> color = Color.Unspecified
    }

    if (extended.value == true){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(
                    //start = 25.dp,
                    //end = 25.dp,
                    top = 15.dp,
                    //bottom = 5.dp
                ),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable { extended.value = !extended.value },
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                    colors = CardDefaults.cardColors(color)
                ) {
                    Row(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = pocket.pocketTitle, fontSize = 18.sp ,fontWeight = FontWeight(500), color = Color.White)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.pocket), contentDescription = "History",
                                modifier = Modifier.fillMaxHeight(0.5f),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "History",
                                tint = Color.White
                            )
                        }
                    }
                }

                Column(modifier = Modifier
                    .fillMaxSize(),
                ) {
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
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(40.dp)
                .clickable { extended.value = !extended.value },
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(color),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = pocket.pocketTitle, fontSize = 18.sp ,fontWeight = FontWeight(500), color = Color.White)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.pocket), contentDescription = "History",
                        modifier = Modifier.fillMaxHeight(0.5f),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "History",
                        tint = Color.White
                    )
                }
            }
        }

        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
            //verticalArrangement = Arrangement.SpaceBetween
        ) {
            //Text(text = note.description, fontSize = 12.sp, maxLines = 8, textAlign = TextAlign.Justify, lineHeight = 13.sp)
        }
    }
}

//@Preview
@Composable
fun HF_NEW_HomeScreenInput(
    title: String = "Lorem Ipsum",
    balance: String = "25000.0",
    painter: Painter = painterResource(id = R.drawable.all),
    tileInfo: String = "Lorem Ipsum",
    clickFlagReturn: (String) -> Unit
){
    var color = Color(100, 70, 170)
    var text = ""
    var clickFlag = remember {
        mutableStateOf("1")
    }

    when(clickFlag.value){
        "1" -> {
            text = "Got Replaced"
        }
        "2" -> {
            color = Color(70, 170, 100)
            text = "Plus+ your input"
        }
        "3" -> {
            color = Color(170, 70, 80)
            text = "Minus- your input"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.White)

                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painter, contentDescription = "", modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp), tint = color
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Rp.$balance",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = text,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clickable {
                                clickFlag.value = "1"
                                clickFlagReturn(clickFlag.value)
                            },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.White),
                        border =
                        if(clickFlag.value == "1"){
                            BorderStroke(4.dp, Color.Yellow)
                        } else {
                            null
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh, contentDescription = "", modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp),
                                tint = color
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Card(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clickable {
                                clickFlag.value = "2"
                                clickFlagReturn(clickFlag.value)
                            },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.White),
                        border =
                        if(clickFlag.value == "2"){
                            BorderStroke(4.dp, Color.Yellow)
                        } else {
                            null
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "", modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp), tint = color
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Card(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clickable {
                                clickFlag.value = "3"
                                clickFlagReturn(clickFlag.value)
                            },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.White),
                        border =
                        if(clickFlag.value == "3"){
                            BorderStroke(4.dp, Color.Yellow)
                        } else {
                            null
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "", modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp), tint = color
                            )
                        }
                    }
                }
            }
    }
}

//@Preview
@Composable
fun HF_NEW_PocketPageInput(){
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
                text = "", label = "Insert Pocket Title",
                onTextChange = {
                    if ((it.all { char -> char.isDigit()} )){

                    }
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            HF_TextField(
                keyboardType = KeyboardType.Number,
                text = "", label = "Insert Pocket Description",
                onTextChange = {
                    if ((it.all { char -> char.isDigit()} )){

                    }
                }
            )
            Spacer(modifier = Modifier.padding(3.dp))
        }
    }
}

//@Preview
@Composable
fun HF_NEW_PocketScreenInput(
    title: String = "Lorem Ipsum",
    balance: String = "25000.0",
    painter: Painter = painterResource(id = R.drawable.all),
    tileInfo: String = "Lorem Ipsum",
    clickFlagReturn: (String) -> Unit
){
    var color = Color(100, 70, 170)
    var text = ""
    var clickFlag = remember {
        mutableStateOf("1")
    }

    when(clickFlag.value){
        "1" -> {
            text = "Re-alocate"
        }
        "2" -> {
            color = Color(70, 170, 100)
            text = "Record income"
        }
        "3" -> {
            color = Color(170, 70, 80)
            text = "Record expense"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Options: $text", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {
                            clickFlag.value = "1"
                            clickFlagReturn(clickFlag.value)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(Color.White),
                    border =
                    if(clickFlag.value == "1"){
                        BorderStroke(4.dp, Color.Yellow)
                    } else {
                        null
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh, contentDescription = "", modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            tint = color
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {
                            clickFlag.value = "2"
                            clickFlagReturn(clickFlag.value)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(Color.White),
                    border =
                    if(clickFlag.value == "2"){
                        BorderStroke(4.dp, Color.Yellow)
                    } else {
                        null
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "", modifier = Modifier
                                .width(30.dp)
                                .height(30.dp), tint = color
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {
                            clickFlag.value = "3"
                            clickFlagReturn(clickFlag.value)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(Color.White),
                    border =
                    if(clickFlag.value == "3"){
                        BorderStroke(4.dp, Color.Yellow)
                    } else {
                        null
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "", modifier = Modifier
                                .width(30.dp)
                                .height(30.dp), tint = color
                        )
                    }
                }
            }
        }
    }
}