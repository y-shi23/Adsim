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

    var centerAdShownCount: Int
        get() = prefs.getInt(KEY_CENTER_AD_SHOWN_COUNT, 0)
        set(value) = prefs.edit().putInt(KEY_CENTER_AD_SHOWN_COUNT, value).apply()

    fun incrementCenterAdShownCount() {
        centerAdShownCount = centerAdShownCount + 1
    }
    
    fun resetCenterAdShownCount() {
        centerAdShownCount = 0
    }
}
