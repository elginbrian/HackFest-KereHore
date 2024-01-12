package com.hackfest.KereHore.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.hackfest.KereHore.signIn.HF_SignInState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hackfest.KereHore.R
import com.hackfest.KereHore.navigation.HF_NavEnum
import com.hackfest.KereHore.widgets.HF_BarButton
import com.hackfest.KereHore.widgets.HF_PopUpCard


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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
    val displayPopUp = remember {
        mutableStateOf(false)
    }
    Scaffold(
        content = {
            Surface(modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(20.dp))
                    if(displayPopUp.value == true){
                        HF_PopUpCard(text = "This feature is not finished yet"){trigger ->
                            if(trigger.isNotEmpty()){
                                displayPopUp.value = !displayPopUp.value
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp),
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
                            displayPopUp.value = true
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    HF_BarButton(text = "HomeScreen"){trigger ->
                        if(trigger.isNotEmpty()){
                            navController.navigate(route = HF_NavEnum.HF_HomeScreen.name)
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    )

}