package io.github.oceanAdsim.ad

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import io.github.oceanAdsim.ui.navigation.NavDestinations
import java.util.Random

object CenterAdManager {
    private var currentActivity: Activity? = null
    private var navController: NavController? = null
    private val handler = Handler(Looper.getMainLooper())
    private val random = Random()
    private var isCenterAdShowing = false
    private var centerAdRunnable: Runnable? = null

    // 广告图片资源名称列表
    private val adImageNames = listOf("ad1", "ad2", "ad3", "ad4", "ad5")

    // 当前显示的广告状态
    var currentAdState by mutableStateOf<CenterAdState?>(null)
        private set

    // 添加状态更新回调
    private var stateUpdateCallback: (() -> Unit)? = null

    data class CenterAdState(
        val isVisible: Boolean,
        val adImageName: String
    )

    // 设置状态更新回调
    fun setStateUpdateCallback(callback: () -> Unit) {
        stateUpdateCallback = callback
    }

    // 检查是否有中间广告正在显示
    fun isShowingCenterAd(): Boolean {
        return isCenterAdShowing && currentAdState?.isVisible == true
    }

    // 初始化
    fun initialize(activity: Activity, controller: NavController) {
        android.util.Log.d("CenterAdManager", "初始化开始")
        currentActivity = activity
        navController = controller
        startCenterAutoAd()
        android.util.Log.d("CenterAdManager", "初始化完成")
    }

    // 显示中间大广告
    fun showCenterAd() {
        if (isCenterAdShowing || currentActivity == null) {
            android.util.Log.d("CenterAdManager", "广告显示被阻止: isShowing=$isCenterAdShowing, activity=${currentActivity != null}")
            return
        }

        android.util.Log.d("CenterAdManager", "开始显示中间广告")
        isCenterAdShowing = true
        val adImageName = adImageNames[random.nextInt(adImageNames.size)]

        currentAdState = CenterAdState(
            isVisible = true,
            adImageName = adImageName
        )

        android.util.Log.d("CenterAdManager", "广告状态已更新: $adImageName")
        // 触发状态更新回调
        stateUpdateCallback?.invoke()
    }

    // 关闭中间广告
    fun closeCenterAd() {
        isCenterAdShowing = false
        currentAdState = null

        // 触发状态更新回调
        stateUpdateCallback?.invoke()
    }

    // 广告点击处理
    fun onAdClick() {
        closeCenterAd()
        navController?.navigate(NavDestinations.AD_LANDING)
    }

    // 启动自动中间广告显示
    private fun startCenterAutoAd() {
        if (centerAdRunnable != null) return

        centerAdRunnable = object : Runnable {
            override fun run() {
                // 40%概率显示中间广告
                if (random.nextInt(100) < 40 && currentActivity != null) {
                    showCenterAd()
                }
                // 显示间隔：20-40秒
                val delay = 20000 + random.nextInt(20000)
                handler.postDelayed(this, delay.toLong())
            }
        }

        // 初始延迟后开始自动显示（10秒后）
        handler.postDelayed(centerAdRunnable!!, 10000)
    }

    // 页面切换时显示广告（增加概率）
    fun showAdOnNavigation() {
        val shouldShow = !isCenterAdShowing && currentActivity != null && random.nextInt(100) < 50
        android.util.Log.d("CenterAdManager", "页面切换广告检查: isShowing=$isCenterAdShowing, activity=${currentActivity != null}, shouldShow=$shouldShow")

        if (shouldShow) {
            // 延迟500ms显示，让页面先加载完成
            handler.postDelayed({
                android.util.Log.d("CenterAdManager", "执行页面切换广告显示")
                showCenterAd()
            }, 500)
        }
    }

    // 停止自动中间广告
    fun stopCenterAutoAd() {
        if (centerAdRunnable != null) {
            handler.removeCallbacks(centerAdRunnable!!)
            centerAdRunnable = null
        }
    }

    // 清理资源
    fun cleanup() {
        stopCenterAutoAd()
        currentActivity = null
        navController = null
        currentAdState = null
    }
}