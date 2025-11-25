package io.github.oceanAdsim.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oceanAdsim.ad.AdSimulator

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "欢迎使用广告模拟器", fontSize = 24.sp)
        Text(
            text = "此应用用于测试AI智能体关闭弹窗广告的能力",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(
            onClick = {
                // 手动触发广告弹窗
                AdSimulator.showAdPopup()
            }
        ) {
            Text("手动显示广告")
        }
    }
}