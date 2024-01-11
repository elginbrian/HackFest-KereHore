package com.hackfest.KereHore.database

data class HF_DummyTemplate(
    val pocketID: String,
    val pocketTitle: String,
    val pocketDescription: String,
    val pocketBalance: String
)

fun HF_GetDummies(): List<HF_DummyTemplate> {
    return listOf(
        HF_DummyTemplate(
            pocketID = "001",
            pocketTitle = "Food and Beverage",
            pocketDescription = "Yum-yum...",
            pocketBalance = "10.000"
        ),

        HF_DummyTemplate(
            pocketID = "002",
            pocketTitle = "Fuel and Gas",
            pocketDescription = "Brrrr....",
            pocketBalance = "25.000"
        ),

        HF_DummyTemplate(
            pocketID = "003",
            pocketTitle = "Saving",
            pocketDescription = "Im gonna be rich!",
            pocketBalance = "100.000"
        )
    )
}