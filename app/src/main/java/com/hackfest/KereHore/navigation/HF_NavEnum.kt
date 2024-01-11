package com.hackfest.KereHore.navigation

enum class HF_NavEnum {
    HF_HomeScreen,
    HF_PocketScreen,
    HF_SubscriptionScreen;
    companion object {
        fun fromRoute(route: String?): HF_NavEnum
                = when(route?.substringBefore("/")){
            HF_HomeScreen.name         -> HF_HomeScreen
            HF_PocketScreen.name       -> HF_PocketScreen
            HF_SubscriptionScreen.name -> HF_SubscriptionScreen
            null -> HF_HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}