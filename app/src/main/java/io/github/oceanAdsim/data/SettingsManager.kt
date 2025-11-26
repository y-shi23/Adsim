package io.github.oceanAdsim.data

import android.content.Context
import android.content.SharedPreferences

object SettingsManager {
    private const val PREF_NAME = "adsim_settings"
    private const val KEY_SHOW_BOTTOM_AD = "show_bottom_ad"
    private const val KEY_CENTER_AD_ONCE = "center_ad_once"
    private const val KEY_CENTER_AD_SHOWN_COUNT = "center_ad_shown_count"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var showBottomAd: Boolean
        get() = prefs.getBoolean(KEY_SHOW_BOTTOM_AD, true)
        set(value) = prefs.edit().putBoolean(KEY_SHOW_BOTTOM_AD, value).apply()

    var centerAdOnce: Boolean
        get() = prefs.getBoolean(KEY_CENTER_AD_ONCE, false)
        set(value) = prefs.edit().putBoolean(KEY_CENTER_AD_ONCE, value).apply()

    // 修改为内存变量，不进行持久化存储
    // 这样每次APP重新启动（进程重建）时，计数器都会重置为0
    var centerAdShownCount: Int = 0

    fun incrementCenterAdShownCount() {
        centerAdShownCount++
    }
    
    fun resetCenterAdShownCount() {
        centerAdShownCount = 0
    }
}
