package com.perrygarg.twinmind.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.perrygarg.twinmind.presentation.navigation.NavRoutes
import com.perrygarg.twinmind.presentation.login.LoginScreen
import com.perrygarg.twinmind.presentation.calendar.CalendarScreen
import com.perrygarg.twinmind.presentation.transcription.TranscriptionScreen
import com.perrygarg.twinmind.presentation.chat.ChatScreen
import com.perrygarg.twinmind.presentation.summary.SummaryScreen
import com.perrygarg.twinmind.presentation.settings.SettingsScreen
import com.perrygarg.twinmind.presentation.saved_summaries.SavedSummariesScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // For now, always start at LOGIN. SplashScreen will handle auth soon.
    val startDestination = NavRoutes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavRoutes.LOGIN) { LoginScreen(navController) }
        composable(NavRoutes.CALENDAR) { CalendarScreen(navController) }
        composable(NavRoutes.TRANSCRIPTION) { TranscriptionScreen(navController) }
        composable(NavRoutes.TRANSCRIPTION_WITH_EVENT) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            TranscriptionScreen(navController, eventId)
        }
        composable(NavRoutes.CHAT) { ChatScreen(navController) }
        composable(NavRoutes.SUMMARY) { SummaryScreen(navController) }
        composable(NavRoutes.SUMMARY_WITH_TRANSCRIPT) { backStackEntry ->
            val transcript = backStackEntry.arguments?.getString("transcript")
            SummaryScreen(navController, transcript)
        }
        composable(NavRoutes.SETTINGS) { SettingsScreen(navController) }
        composable(NavRoutes.SAVED_SUMMARIES) { SavedSummariesScreen(navController) }
    }
} 