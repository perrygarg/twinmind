package com.perrygarg.twinmind

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.perrygarg.twinmind.presentation.login.LoginScreen
import com.perrygarg.twinmind.presentation.dashboard.ChatScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("chat") {
            ChatScreen()
        }
    }
} 