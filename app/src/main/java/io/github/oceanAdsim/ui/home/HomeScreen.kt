package io.github.oceanAdsim.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oceanAdsim.ad.AdSimulator
import io.github.oceanAdsim.ad.CenterAdManager
import io.github.oceanAdsim.ui.components.CenterAdOverlay

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // 主内容
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "欢迎使用Adsim", fontSize = 24.sp)
            Text(
                text = "此应用用于测试弹窗广告的能力\n底部广告和中间广告都会随机出现",
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = {
                    // 手动触发底部广告弹窗
                    AdSimulator.showAdPopup()
                }
            ) {
                Text("手动显示底部广告")
            }

            Button(
                onClick = {
                    // 手动触发中间广告
                    CenterAdManager.showCenterAd()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("手动显示中间广告")
            }
        }

        // 中间广告叠加层
        CenterAdOverlay()
    }
}