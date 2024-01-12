package com.hackfest.KereHore.signIn

data class HF_SignInResult(
    val data: UserData?,
    val errorMessege: String?
)

data class UserData(
    val userID: String,
    val userName: String?,
    val profilePictureURL: String?
)