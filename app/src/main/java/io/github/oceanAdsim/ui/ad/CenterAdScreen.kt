package io.github.oceanAdsim.ui.ad

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import io.github.oceanAdsim.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAdScreen(
    modifier: Modifier = Modifier,
    adImageName: String,
    onClose: () -> Unit,
    onAdClick: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val resourceId = context.resources.getIdentifier(
        adImageName,
        "drawable",
        context.packageName
    )

    // 计算最大高度：屏幕高度的70% - 关闭按钮区域
    val maxHeight = (configuration.screenHeightDp.dp * 0.7f) - 60.dp

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 广告容器 - 直接使用Column，移除Card外框
        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f) // 增加宽度比例到95%
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 广告图片区域 - 限制最大高度
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = maxHeight) // 限制最大高度
                    .clickable { onAdClick() }
            ) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = "广告图片",
                    modifier = Modifier
                        .fillMaxSize(), // 填充整个Box区域
                    contentScale = ContentScale.Fit // 确保完整显示且保持比例
                )
            }

            // 关闭按钮区域
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp), // 稍微减少上下间距
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.7f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "关闭广告",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AdLandingScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "广告跳转成功！",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "您已成功点击广告并跳转到此页面。",
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "这是一个模拟的广告跳转页面。\n在实际应用中，这里会显示广告主的落地页内容。",
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = {
                navController?.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "返回",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}