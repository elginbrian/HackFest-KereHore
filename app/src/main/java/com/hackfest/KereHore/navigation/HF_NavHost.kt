package com.hackfest.KereHore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hackfest.KereHore.screen.HF_HomeScreen
import com.hackfest.KereHore.screen.HF_PocketScreen
import com.hackfest.KereHore.screen.HF_SubscriptionScreen
import com.hackfest.KereHore.screen.HF_ViewModel
import com.hackfest.KereHore.screen.HF_ViewModel2

@Composable
fun HF_NavHost(
    HF_ViewModel: HF_ViewModel,
    HF_ViewModel2: HF_ViewModel2
){
    val navController = rememberNavController()
    val pocket        = HF_ViewModel.pocketList.collectAsState().value
    val totalBalance  = HF_ViewModel2.totalBalance.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = HF_NavEnum.HF_HomeScreen.name
    ){
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

        composable(HF_NavEnum.HF_SubscriptionScreen.name){
            HF_SubscriptionScreen(
                //navController = navController
            )
        }
    }
}