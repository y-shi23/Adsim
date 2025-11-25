package io.github.oceanAdsim.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.oceanAdsim.ui.home.HomeScreen
import io.github.oceanAdsim.ui.message.MessageScreen
import io.github.oceanAdsim.ui.profile.ProfileScreen
import io.github.oceanAdsim.ui.ad.AdLandingScreen
import io.github.oceanAdsim.ui.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.HOME) {
        composable(NavDestinations.HOME) {
            HomeScreen()
        }
        composable(NavDestinations.MESSAGE) {
            MessageScreen(navController)
        }
        composable(NavDestinations.PROFILE) {
            ProfileScreen(navController)
        }
        composable(NavDestinations.AD_LANDING) {
            AdLandingScreen(navController)
        }
        composable(NavDestinations.SETTINGS) {
            SettingsScreen(navController)
        }
    }
}