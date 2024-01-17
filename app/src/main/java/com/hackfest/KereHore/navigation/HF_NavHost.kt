package com.hackfest.KereHore.navigation

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import com.hackfest.KereHore.screen.HF_HomeScreen
import com.hackfest.KereHore.screen.HF_PocketScreen
import com.hackfest.KereHore.screen.HF_SignInScreen
import com.hackfest.KereHore.screen.HF_SignInViewModel
import com.hackfest.KereHore.screen.HF_ViewModel
import com.hackfest.KereHore.screen.HF_ViewModel2
import com.hackfest.KereHore.signIn.HF_GoogleAuthUIClient
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.hackfest.KereHore.screen.HF_NEW_HistoryScreen
import com.hackfest.KereHore.screen.HF_NEW_HomeScreen
import com.hackfest.KereHore.screen.HF_NEW_PocketPage
import com.hackfest.KereHore.screen.HF_NEW_PocketScreen


@Composable
fun HF_NavHost(
    HF_ViewModel: HF_ViewModel,
    HF_ViewModel2: HF_ViewModel2,
    HF_SignInViewModel: HF_SignInViewModel,
    appContext: android.content.Context
) {
    val googleAuthUIClient by lazy {
        HF_GoogleAuthUIClient(
            context = appContext,
            oneTapClient = Identity.getSignInClient(appContext)
        )
    }
    val navController = rememberNavController()
    val pocket = HF_ViewModel.pocketList.collectAsState().value
    val totalBalance = HF_ViewModel2.totalBalance.collectAsState().value
    val state = HF_SignInViewModel.state.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = HF_NavEnum.HF_NEW_HomeScreen.name
    ) {
        composable(HF_NavEnum.HF_SignInScreen.name) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUIClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            HF_SignInViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            HF_SignInScreen(
                navController = navController,
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccesful) {
                if (state.isSignInSuccesful) {
                    Toast.makeText(appContext, "Sign in successful", Toast.LENGTH_LONG).show()
                    navController.navigate(HF_NavEnum.HF_HomeScreen.name)
                }
            }
        }

        composable(HF_NavEnum.HF_HomeScreen.name){
            HF_HomeScreen(
                navController      = navController,

                addPocketObject    = { HF_ViewModel.addPocket(it) },
                removePocketObject = { HF_ViewModel.removePocket(it) },
                deleteAllPocket    = { HF_ViewModel.deleteAllPocket(it) },
                pocket             = pocket,

                totalBalance       = totalBalance,
                addTotalBalance    = { HF_ViewModel2.addTotalBalance(it) },
                deleteAllBalance   = { HF_ViewModel2.deleteAllBalance(it) }
            )
        }

        composable(HF_NavEnum.HF_NEW_HomeScreen.name){
            HF_NEW_HomeScreen(
                navController = navController,
                pocket = pocket,
                totalBalance = totalBalance,
                addTotalBalance = { HF_ViewModel2.addTotalBalance(it) },
                deleteAllBalance = { HF_ViewModel2.deleteAllBalance(it) }
            )
        }

        composable(HF_NavEnum.HF_NEW_PocketPage.name){
            HF_NEW_PocketPage(
                navController = navController,
                pocket = pocket,
                addPocketObject = { HF_ViewModel.addPocket(it) },
                removePocketObject = { HF_ViewModel.removePocket(it) },
                deleteAllPocket = { HF_ViewModel.deleteAllPocket(it) },
                totalBalance = totalBalance
            )
        }

        composable(HF_NavEnum.HF_NEW_HistoryScreen.name){
            HF_NEW_HistoryScreen(navController = navController, pocket = pocket, totalBalance = totalBalance)
        }

        composable(
            HF_NavEnum.HF_NEW_PocketScreen.name+"/{pocket}",
            arguments = listOf(navArgument(name = "pocket"){type = NavType.StringType})
        ){
            backStackEntry ->
            HF_NEW_PocketScreen(
                navController = navController, backStackEntry.arguments?.getString("pocket"),
                pocketList = pocket,
                totalBalance = totalBalance,
                addPocketBalance = { HF_ViewModel.updatePocket(pocketID = it.first, pocketBalance = it.second)} ,
                reportSpending = { HF_ViewModel.reportSpending(pocketID = it.first, pocketSpending = it.second, pocketBalance = it.third) },
                deleteThisPocket = { HF_ViewModel.removePocket(it) },
                addTotalBalance = { HF_ViewModel2.addTotalBalance(it) }
            )
        }
        composable(
            HF_NavEnum.HF_PocketScreen.name+"/{pocket}",
            arguments = listOf(navArgument(name = "pocket"){type = NavType.StringType})
        ){
                backStackEntry ->
            HF_PocketScreen(
                navController      = navController, backStackEntry.arguments?.getString("pocket"),

                pocketList         = pocket,
                addPocketBalance   = { HF_ViewModel.updatePocket(pocketID = it.first, pocketBalance = it.second)},
                reportSpending     = { HF_ViewModel.reportSpending(pocketID = it.first, pocketSpending = it.second, pocketBalance = it.third)},
                deleteThisPocket   = { HF_ViewModel.removePocket(it)},

                totalBalance       = totalBalance,
                //updateTotalBalance = { HF_ViewModel2.updateTotalBalance(totalBalanceID = it.first, totalBalance = it.second)}
                addTotalBalance    = { HF_ViewModel2.addTotalBalance(it)}
            )
        }
    }
}