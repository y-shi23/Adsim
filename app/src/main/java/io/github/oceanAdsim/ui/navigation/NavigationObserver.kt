package io.github.oceanAdsim.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import io.github.oceanAdsim.ad.AdSimulator
import io.github.oceanAdsim.ad.CenterAdManager
import java.util.Random

class NavigationObserver : NavController.OnDestinationChangedListener {
    private val random = Random()
    private val bottomAdProbability = 0.2f // 20%的概率显示底部广告
    private val centerAdProbability = 0.4f // 40%的概率显示中间广告

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: android.os.Bundle?
    ) {
        android.util.Log.d("NavigationObserver", "导航到页面: ${destination.route}")

        // 当导航到新页面时，随机决定是否显示底部广告
        if (random.nextFloat() < bottomAdProbability) {
            android.util.Log.d("NavigationObserver", "将显示底部广告")
            // 稍微延迟显示广告，让页面先加载完成
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                AdSimulator.showAdPopup()
            }, 500 + random.nextInt(1000).toLong())
        }

        // 随机决定是否显示中间广告
        if (random.nextFloat() < centerAdProbability) {
            android.util.Log.d("NavigationObserver", "将检查中间广告显示")
            // 使用CenterAdManager的页面切换广告逻辑
            CenterAdManager.showAdOnNavigation()
        }
    }
}