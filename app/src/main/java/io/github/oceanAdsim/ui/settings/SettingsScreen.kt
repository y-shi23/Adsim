package io.github.oceanAdsim.ui.settings

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import io.github.oceanAdsim.ui.components.CenterAdOverlay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun SettingsScreen(navController: NavController? = null) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            // 返回按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 使用更安全的返回方法
                IconButton(
                    onClick = {
                        navController?.popBackStack()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "返回"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "设置",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // 设置选项列表
            SettingSection(title = "广告设置") {
                SettingItem(
                    title = "自动显示广告",
                    subtitle = "开启后应用会自动显示各种广告"
                )
                SettingItem(
                    title = "广告频率",
                    subtitle = "调整广告显示的频率"
                )
                SettingItem(
                    title = "广告类型",
                    subtitle = "选择要显示的广告类型"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            SettingSection(title = "通用设置") {
                SettingItem(
                    title = "通知设置",
                    subtitle = "管理应用通知"
                )
                SettingItem(
                    title = "隐私设置",
                    subtitle = "管理数据隐私"
                )
                SettingItem(
                    title = "关于应用",
                    subtitle = "版本信息和帮助"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            SettingSection(title = "测试设置") {
                SettingItem(
                    title = "清除所有广告",
                    subtitle = "立即清除当前显示的所有广告"
                )
                SettingItem(
                    title = "重置设置",
                    subtitle = "恢复到默认设置"
                )
            }
        }

        // 中间广告叠加层
        CenterAdOverlay()
    }
}

@Composable
private fun SettingSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .let { modifier ->
                    // 添加轻微的阴影效果
                    if (true) {
                        modifier // 在实际项目中可以添加阴影
                    } else {
                        modifier
                    }
                }
        ) {
            content()
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // 可选：添加箭头图标表示可以点击
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = ">",
                fontSize = 18.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
    }

    // 分割线（除了最后一项）
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(1.dp),
        color = Color.LightGray
    )
}