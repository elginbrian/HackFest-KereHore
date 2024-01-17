package com.hackfest.KereHore.navigation

enum class HF_NavEnum {
    HF_SignInScreen,
    HF_HomeScreen,
    HF_PocketScreen,

    HF_NEW_HomeScreen,
    HF_NEW_PocketPage,
    HF_NEW_PocketScreen,
    HF_NEW_HistoryScreen;
    companion object {
        fun fromRoute(route: String?): HF_NavEnum
                = when(route?.substringBefore("/")){
            HF_SignInScreen.name       -> HF_SignInScreen
            HF_HomeScreen.name         -> HF_HomeScreen
            HF_PocketScreen.name       -> HF_PocketScreen

            HF_NEW_HomeScreen.name     -> HF_NEW_HomeScreen
            HF_NEW_PocketPage.name     -> HF_NEW_PocketPage
            HF_NEW_PocketScreen.name   -> HF_NEW_PocketScreen
            HF_NEW_HistoryScreen.name  -> HF_NEW_HistoryScreen
            null -> HF_NEW_HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}