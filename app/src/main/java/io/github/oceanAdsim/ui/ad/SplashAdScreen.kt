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
                .padding(top = 40.dp, end = 20.dp) // 增加顶部边距以避开状态栏，调整右侧边距
                .align(Alignment.TopEnd), // 对齐到右上角
            contentAlignment = Alignment.CenterEnd
        ) {
            // 现代化跳过按钮
            Surface(
                onClick = onSkip,
                enabled = isSkipEnabled,
                shape = RoundedCornerShape(50), // 完全圆角
                color = Color.Black.copy(alpha = 0.4f), // 半透明黑色背景
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)), // 细微边框
                modifier = Modifier.wrapContentSize()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (!isSkipEnabled) {
                        Text(
                            text = "${countdown}s",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(12.dp)
                                .background(Color.White.copy(alpha = 0.5f))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    
                    Text(
                        text = "跳过",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
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