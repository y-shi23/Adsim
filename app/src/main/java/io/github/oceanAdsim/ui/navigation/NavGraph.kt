package io.github.oceanAdsim.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.oceanAdsim.ui.home.HomeScreen
import io.github.oceanAdsim.ui.message.MessageScreen
import io.github.oceanAdsim.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.HOME) {
        composable(NavDestinations.HOME) {
            HomeScreen()
        }
        composable(NavDestinations.MESSAGE) {
            MessageScreen()
        }
        composable(NavDestinations.PROFILE) {
            ProfileScreen()
        }
    }
}