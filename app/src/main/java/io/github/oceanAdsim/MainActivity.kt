package io.github.oceanAdsim

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.github.oceanAdsim.ad.AdSimulator
import io.github.oceanAdsim.ad.CenterAdManager
import io.github.oceanAdsim.ui.navigation.BottomNavigationBar
import io.github.oceanAdsim.ui.theme.AdsimTheme
import io.github.oceanAdsim.ui.navigation.NavDestinations
import io.github.oceanAdsim.ui.ad.SplashAdScreen
import java.util.Random

class MainActivity : ComponentActivity() {
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AdsimTheme {
                SplashAdWrapper()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止自动广告
        AdSimulator.stopAutoAd()
        CenterAdManager.stopCenterAutoAd()
        CenterAdManager.cleanup()
    }
}


@Composable
fun SplashAdWrapper() {
    // 控制是否显示开屏广告
    var showSplashAd by remember { mutableStateOf(true) }
    var showMainContent by remember { mutableStateOf(false) }

    if (showSplashAd) {
        // 显示开屏广告
        SplashAdScreen(
            onSkip = {
                showSplashAd = false
                showMainContent = true
            },
            onAdClick = {
                // 点击开屏广告，直接进入主界面
                showSplashAd = false
                showMainContent = true
            }
        )
    } else if (showMainContent) {
        // 显示主应用内容
        SetupNavHost()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavHost() {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    // 初始化广告管理器
    LaunchedEffect(navController) {
        CenterAdManager.initialize(
            activity = context as ComponentActivity,
            controller = navController
        )
        // 设置AdSimulator的导航控制器
        AdSimulator.setCurrentActivity(context as ComponentActivity, navController)
    }

    // 应用启动后1秒显示中间大广告
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            CenterAdManager.showCenterAd()
        }, 1000)
    }

    BottomNavigationBar(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AdsimTheme {
        SetupNavHost()
    }
}