package com.example.dragpicturegoback.utils

import android.graphics.Color
import androidx.viewpager2.widget.ViewPager2

object Config {
    var DEBUG: Boolean = true
    var OFFSCREEN_PAGE_LIMIT: Int = 1
    var VIEWER_ORIENTATION: Int = ViewPager2.ORIENTATION_HORIZONTAL
    var VIEWER_BACKGROUND_COLOR: Int = Color.BLACK
    var DURATION_TRANSITION: Long = 300L
    var DURATION_BG: Long = 200L
    var SWIPE_DISMISS: Boolean = true
    var SWIPE_TOUCH_SLOP = 4f
    var DISMISS_FRACTION: Float = 0.12f

    var TRANSITION_OFFSET_Y = 0

    // adapter 中的 tag 标记 Key
    const val ADAPTER_PHOTO_VIEW_DATA = 0
    const val ADAPTER_PHOTO_VIEW_ID = 1
    const val ADAPTER_PHOTO_VIEW = 2
}