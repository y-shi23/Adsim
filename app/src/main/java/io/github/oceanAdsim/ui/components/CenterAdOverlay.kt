package io.github.oceanAdsim.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.oceanAdsim.ad.CenterAdManager
import io.github.oceanAdsim.ui.ad.CenterAdScreen

/**
 * 可重用的中间广告叠加层组件
 * 可以在任何页面上使用
 */
@Composable
fun CenterAdOverlay(
    modifier: Modifier = Modifier
) {
    // 本地状态用于跟踪中间广告
    var currentAdState by remember { mutableStateOf<CenterAdManager.CenterAdState?>(null) }

    // 初始化时设置状态更新回调
    LaunchedEffect(Unit) {
        // 设置状态更新回调
        CenterAdManager.setStateUpdateCallback {
            // 当CenterAdManager状态更新时，同步本地状态
            currentAdState = CenterAdManager.currentAdState
        }

        // 初始同步状态
        currentAdState = CenterAdManager.currentAdState
    }

    // 监听CenterAdManager状态变化
    LaunchedEffect(CenterAdManager.currentAdState) {
        currentAdState = CenterAdManager.currentAdState
    }

    // 中间广告叠加层
    currentAdState?.let { adState ->
        if (adState.isVisible) {
            CenterAdScreen(
                modifier = modifier,
                adImageName = adState.adImageName,
                onClose = {
                    CenterAdManager.closeCenterAd()
                },
                onAdClick = {
                    CenterAdManager.onAdClick()
                }
            )
        }
    }
}