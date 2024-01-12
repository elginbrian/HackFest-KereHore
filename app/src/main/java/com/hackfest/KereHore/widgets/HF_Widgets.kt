package com.hackfest.KereHore.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackfest.KereHore.database.HF_DummyTemplate
import com.hackfest.KereHore.database.HF_GetDummies
import com.hackfest.KereHore.model.HF_PocketObject
import java.util.UUID

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
            Icon(imageVector = imageVector, contentDescription = "Add Pocket")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HF_TextField(
    modifier: Modifier = Modifier,
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
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.inverseSurface),
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

@Preview
@Composable
fun HF_BarButton(
    text: String = "Lorem Ipsum",
    whenWidgetClicked: (String) -> Unit = {}
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { whenWidgetClicked("trigger") },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 18.sp)
        }
    }
}

//@Preview
@Composable
fun Preview(){
   // HF_TextField(onTextChange = {})
}