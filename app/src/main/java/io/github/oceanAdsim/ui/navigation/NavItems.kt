package io.github.oceanAdsim.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : NavItem(NavDestinations.HOME, "首页", Icons.Filled.Home)
    object Message : NavItem(NavDestinations.MESSAGE, "消息", Icons.Filled.Email)
    object Profile : NavItem(NavDestinations.PROFILE, "我的", Icons.Filled.Person)
}

val navItems = listOf(NavItem.Home, NavItem.Message, NavItem.Profile)