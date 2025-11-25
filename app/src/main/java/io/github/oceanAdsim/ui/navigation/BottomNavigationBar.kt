package io.github.oceanAdsim.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.oceanAdsim.R
import androidx.compose.ui.graphics.Color
import io.github.oceanAdsim.ad.CenterAdManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // 广告状态，定期检查更新
    var isAdShowing by remember { mutableStateOf(CenterAdManager.isShowingCenterAd()) }

    // 定期检查广告状态变化（每500ms检查一次）
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            val newAdState = CenterAdManager.isShowingCenterAd()
            if (newAdState != isAdShowing) {
                isAdShowing = newAdState
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("广告模拟器") },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            when (item.route) {
                                NavDestinations.HOME -> Icons.Filled.Home
                                NavDestinations.MESSAGE -> Icons.Filled.Email
                                NavDestinations.PROFILE -> Icons.Filled.Person
                                else -> Icons.Filled.Home
                            }
                        },
                        label = { Text(item.label) },
                        selected = currentDestination?.route == item.route,
                        enabled = !isAdShowing, // 当广告显示时禁用导航项
                        onClick = {
                            // 检查是否有中间广告正在显示，如果有则禁止导航
                            if (!isAdShowing) {
                                navController.navigate(item.route) {
                                    // 避免重复导航到相同的目的地
                                    launchSingleTop = true
                                    // 清除导航栈，返回到顶层
                                    popUpTo(NavDestinations.HOME) {
                                        saveState = true
                                    }
                                    // 恢复保存的状态
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                NavGraph(navController)
            }
        }
    )
}