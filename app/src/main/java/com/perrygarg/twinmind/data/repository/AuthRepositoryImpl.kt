package com.perrygarg.twinmind.data.repository

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth, private val googleSignInClient: GoogleSignInClient) : AuthRepository {
    override fun startSignIn(activity: Activity): Intent {
        Log.d("AuthRepository", "Starting Google Sign-In intent")
        return googleSignInClient.signInIntent
    }

    override suspend fun handleSignInResult(data: Intent?): Result<FirebaseUser> {
        return try {
            Log.d("AuthRepository", "Handling Google Sign-In result")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            Log.d("AuthRepository", "GoogleSignInAccount: idToken=${account.idToken}, email=${account.email}")
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user
            Log.d("AuthRepository", "Firebase signInWithCredential success: user=${user?.uid}, email=${user?.email}")
            if (user != null) Result.success(user) else Result.failure(Exception("No user found"))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error in handleSignInResult", e)
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        val user = auth.currentUser
        Log.d("AuthRepository", "getCurrentUser: ${user?.uid}, email: ${user?.email}")
        return user
    }

    override fun isUserSignedIn(): Boolean {
        val isSignedIn = auth.currentUser != null
        Log.d("AuthRepository", "isUserSignedIn: $isSignedIn")
        return isSignedIn
    }

    override fun signOut() {
        Log.d("AuthRepository", "Signing out user")
        auth.signOut()
        googleSignInClient.signOut()
    }

    companion object {
        fun build(activity: Activity): AuthRepositoryImpl {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(com.perrygarg.twinmind.R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(activity, gso)
            val auth = FirebaseAuth.getInstance()
            Log.d("AuthRepository", "AuthRepositoryImpl built with GoogleSignInClient and FirebaseAuth")
            return AuthRepositoryImpl(auth, googleSignInClient)
        }
    }
} 