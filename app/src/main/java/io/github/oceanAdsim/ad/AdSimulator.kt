package io.github.oceanAdsim.ad

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import java.util.Random

object AdSimulator {
    private var currentActivity: Activity? = null
    private val handler = Handler(Looper.getMainLooper())
    private val random = Random()
    private var isAdShowing = false
    private var autoAdRunnable: Runnable? = null

    // 广告内容模板
    private val adTemplates = listOf(
        AdTemplate("限时优惠", "全场商品5折起，立即抢购！", "去看看", 3000),
        AdTemplate("新品推荐", "新品上市，限时免费试用！", "了解详情", 4000),
        AdTemplate("会员福利", "开通会员享受专属特权", "开通会员", 5000),
        AdTemplate("热门游戏", "热门游戏推荐，点击下载", "下载游戏", 3500),
        AdTemplate("直播预告", "今晚8点直播，精彩不容错过", "预约直播", 4500)
    )

    // 设置当前活动
    fun setCurrentActivity(activity: Activity) {
        currentActivity = activity
        // 启动自动广告
        startAutoAd()
    }

    // 手动显示广告弹窗
    fun showAdPopup() {
        if (isAdShowing || currentActivity == null) return
        
        isAdShowing = true
        val adTemplate = adTemplates[random.nextInt(adTemplates.size)]
        
        currentActivity?.runOnUiThread {
            val popupView = createAdPopupView(adTemplate)
            val popupWindow = android.widget.PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            
            // 设置弹窗属性
            popupWindow.isFocusable = true
            popupWindow.isOutsideTouchable = false
            popupWindow.showAtLocation(
                currentActivity!!.window.decorView,
                Gravity.BOTTOM,
                0,
                0
            )
            
            // 设置自动关闭
            handler.postDelayed({
                try {
                    popupWindow.dismiss()
                    isAdShowing = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, adTemplate.displayTime)
        }
    }

    // 创建广告弹窗视图
    private fun createAdPopupView(template: AdTemplate): LinearLayout {
        val context = currentActivity!!
        val container = LinearLayout(context)
        container.orientation = LinearLayout.VERTICAL
        container.setBackgroundColor(Color.WHITE)
        container.setPadding(20, 20, 20, 20)
        
        // 添加广告标题
        val title = TextView(context)
        title.text = template.title
        title.textSize = 18f
        title.setTextColor(Color.BLACK)
        title.setPadding(0, 0, 0, 10)
        container.addView(title)
        
        // 添加广告内容
        val content = TextView(context)
        content.text = template.content
        content.textSize = 16f
        content.setTextColor(Color.parseColor("#666666"))
        content.setPadding(0, 0, 0, 20)
        container.addView(content)
        
        // 添加按钮容器
        val buttonContainer = LinearLayout(context)
        buttonContainer.orientation = LinearLayout.HORIZONTAL
        buttonContainer.gravity = Gravity.END
        
        // 添加关闭按钮
        val closeButton = Button(context)
        closeButton.text = "关闭"
        closeButton.setBackgroundColor(Color.parseColor("#E0E0E0"))
        closeButton.setTextColor(Color.BLACK)
        closeButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { marginEnd = 10 }
        closeButton.setOnClickListener {
            // 标记广告为已关闭
            isAdShowing = false
            
            // 直接从当前视图层次结构中移除容器
            try {
                val parent = container.parent as? ViewGroup
                parent?.removeView(container)
            } catch (e: Exception) {
                // 处理可能的异常
                e.printStackTrace()
            }
        }
        buttonContainer.addView(closeButton)
        
        // 添加广告操作按钮
        val actionButton = Button(context)
        actionButton.text = template.actionText
        actionButton.setBackgroundColor(Color.BLUE)
        actionButton.setTextColor(Color.WHITE)
        actionButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        buttonContainer.addView(actionButton)
        
        container.addView(buttonContainer)
        return container
    }

    // 启动自动广告显示
    private fun startAutoAd() {
        if (autoAdRunnable != null) return
        
        autoAdRunnable = object : Runnable {
            override fun run() {
                if (random.nextBoolean() && currentActivity != null) {
                    showAdPopup()
                }
                // 随机延迟再次显示广告（10-30秒）
                handler.postDelayed(this, 10000 + random.nextInt(20000).toLong())
            }
        }
        
        // 初始延迟后开始自动显示广告
        handler.postDelayed(autoAdRunnable!!, 5000)
    }

    // 停止自动广告
    fun stopAutoAd() {
        if (autoAdRunnable != null) {
            handler.removeCallbacks(autoAdRunnable!!)
            autoAdRunnable = null
        }
    }

    // 广告模板数据类
    data class AdTemplate(
        val title: String,
        val content: String,
        val actionText: String,
        val displayTime: Long // 显示时间（毫秒）
    )
}
