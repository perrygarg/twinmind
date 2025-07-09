package com.perrygarg.twinmind.presentation.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.data.repository.AuthRepository
import com.perrygarg.twinmind.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val signInIntent: Intent? = null,
    val isAlreadyLoggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    private var authRepository: AuthRepository? = null

    fun initializeAuth(activity: Activity) {
        if (authRepository == null) {
            authRepository = AuthRepositoryImpl.build(activity)
        }
    }

    fun checkIfAlreadyLoggedIn(activity: Activity) {
        initializeAuth(activity)
        val alreadyLoggedIn = authRepository?.isUserSignedIn() == true
        _uiState.value = _uiState.value.copy(isAlreadyLoggedIn = alreadyLoggedIn)
    }

    fun signInWithGoogle(activity: Activity) {
        android.util.Log.d("LoginViewModel", "signInWithGoogle called")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                android.util.Log.d("LoginViewModel", "Initializing auth...")
                initializeAuth(activity)
                android.util.Log.d("LoginViewModel", "Starting sign-in...")
                val intent = authRepository?.startSignIn(activity)
                android.util.Log.d("LoginViewModel", "Sign-in intent created: ${intent != null}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    signInIntent = intent
                )
            } catch (e: Exception) {
                android.util.Log.e("LoginViewModel", "Error in signInWithGoogle", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to start sign-in: ${e.message}"
                )
            }
        }
    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = authRepository?.handleSignInResult(data)
                result?.fold(
                    onSuccess = { user ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            signInIntent = null,
                            isAlreadyLoggedIn = true
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Sign-in failed: ${exception.message}",
                            signInIntent = null,
                            isAlreadyLoggedIn = false
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Sign-in failed: ${e.message}",
                    signInIntent = null,
                    isAlreadyLoggedIn = false
                )
            }
        }
    }

    fun clearSignInIntent() {
        _uiState.value = _uiState.value.copy(signInIntent = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
} 