package com.hackfest.KereHore.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hackfest.KereHore.signIn.HF_SignInState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_BarButton


@Composable
fun HF_SignInScreen(
    navController: NavController,
    state: HF_SignInState,
    onSignInClick: () -> Unit
){
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = "HF24-39 / Super Square", fontSize = 12.sp)
            Text(text = "Hackfest second stage submission", fontSize = 12.sp)
            Text(text = "Disclaimer: UI of this app is still temporary", fontSize = 12.sp)
            Spacer(modifier = Modifier.padding(10.dp))

            HF_BarButton(text = "Sign In"){ trigger ->
                if(trigger.isNotEmpty()){
                    onSignInClick
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            HF_BarButton(text = "HomeScreen"){trigger ->
                if(trigger.isNotEmpty()){
                    navController.navigate(route = HF_NavEnum.HF_HomeScreen.name)
                }
            }
            Spacer(modifier = Modifier.padding(15.dp))
        }
    }
}