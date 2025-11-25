package io.github.oceanAdsim

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.github.oceanAdsim.ad.AdSimulator
import io.github.oceanAdsim.ui.navigation.BottomNavigationBar
import io.github.oceanAdsim.ui.navigation.NavigationObserver
import io.github.oceanAdsim.ui.theme.AdsimTheme
import java.util.Random

class MainActivity : ComponentActivity() {
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // 设置当前活动到广告模拟器
        AdSimulator.setCurrentActivity(this)
        
        setContent {
            AdsimTheme {
                SetupNavHost()
            }
        }
        
        // 应用启动后随机延迟显示广告（3-8秒）
        Handler(Looper.getMainLooper()).postDelayed({
            if (random.nextBoolean()) {
                AdSimulator.showAdPopup()
            }
        }, 3000 + random.nextInt(5000).toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止自动广告
        AdSimulator.stopAutoAd()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavHost() {
    val navController = rememberNavController()
    
    // 添加导航观察者，在页面切换时可能显示广告
    androidx.compose.runtime.DisposableEffect(navController) {
        val observer = NavigationObserver()
        navController.addOnDestinationChangedListener(observer)
        
        onDispose {
            navController.removeOnDestinationChangedListener(observer)
        }
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