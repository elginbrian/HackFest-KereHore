package com.hackfest.KereHore.signIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.hackfest.KereHore.R
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class HF_GoogleAuthUIClient (
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): HF_SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIDToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIDToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            HF_SignInResult(
                data = user?.run {
                    UserData(
                        userID = uid,
                        userName = displayName,
                        profilePictureURL = photoUrl?.toString()
                    )
                },
                errorMessege = null
            )
        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            HF_SignInResult(
                data = null,
                errorMessege = e.message
            )
        }
    }

    suspend fun signOut(){
        try {
           oneTapClient.signOut().await()
           auth.signOut()

        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userID = uid,
            userName = displayName,
            profilePictureURL = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}