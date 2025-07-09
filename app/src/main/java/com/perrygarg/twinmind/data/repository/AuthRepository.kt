package com.perrygarg.twinmind.data.repository

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun startSignIn(activity: Activity): Intent
    suspend fun handleSignInResult(data: Intent?): Result<FirebaseUser>
    fun getCurrentUser(): FirebaseUser?
    fun isUserSignedIn(): Boolean
    fun signOut()
} 