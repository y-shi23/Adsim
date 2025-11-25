package io.github.oceanAdsim.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import io.github.oceanAdsim.ad.AdSimulator
import java.util.Random

class NavigationObserver : NavController.OnDestinationChangedListener {
    private val random = Random()
    private val adProbability = 0.6f // 60%的概率在页面切换时显示广告

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: android.os.Bundle?
    ) {
        // 当导航到新页面时，随机决定是否显示广告
        if (random.nextFloat() < adProbability) {
            // 稍微延迟显示广告，让页面先加载完成
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                AdSimulator.showAdPopup()
            }, 500 + random.nextInt(1000).toLong())
        }
    }
}