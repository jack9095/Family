package com.example.dragpicturegoback.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.dragpicturegoback.utils.TransitionEndHelper
import com.example.dragpicturegoback.utils.TransitionStartHelper

/**
 * 滑动消失的布局
 * https://www.jianshu.com/p/72d1959a7c56
 * 在 Kotlin 中 @JvmOverloads 注解的作用就是：
 * 在有默认参数值的方法中使用 @JvmOverloads 注解，则 Kotlin 就会暴露多个重载方法
 */
class InterceptLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {
    override fun onInterceptTouchEvent(ev: MotionEvent?) =
            TransitionStartHelper.animating || TransitionEndHelper.animating
}