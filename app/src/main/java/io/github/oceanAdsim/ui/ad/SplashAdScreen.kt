package io.github.oceanAdsim.ui.ad

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oceanAdsim.R
import android.os.Handler
import android.os.Looper

@Composable
fun SplashAdScreen(
    onSkip: () -> Unit,
    onAdClick: () -> Unit
) {
    val configuration = LocalConfiguration.current

    // 倒计时状态
    var countdown by remember { mutableStateOf(3) }
    val isSkipEnabled = countdown <= 0

    // 倒计时逻辑
    LaunchedEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        countdown = 3
        handler.postDelayed({ countdown = 2 }, 1000)
        handler.postDelayed({ countdown = 1 }, 2000)
        handler.postDelayed({
            countdown = 0
            onSkip()
        }, 3000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 开屏广告图片
        Image(
            painter = painterResource(id = R.drawable.kaipin),
            contentDescription = "开屏广告",
            modifier = Modifier
                .fillMaxSize()
                .clickable { onAdClick() },
            contentScale = ContentScale.Crop
        )

        // 顶部跳过按钮区域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.TopEnd
        ) {
            // 跳过按钮
            Button(
                onClick = onSkip,
                enabled = isSkipEnabled,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isSkipEnabled)
                            Color.White.copy(alpha = 0.9f)
                        else
                            Color.Gray.copy(alpha = 0.7f)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (isSkipEnabled) Color.Black else Color.White
                )
            ) {
                if (isSkipEnabled) {
                    Text(
                        text = "跳过",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        text = "$countdown",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // 底部广告标识
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomStart
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp)),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Text(
                    text = "广告",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}