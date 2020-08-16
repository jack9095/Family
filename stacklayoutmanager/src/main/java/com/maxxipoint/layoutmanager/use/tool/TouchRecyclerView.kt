package com.maxxipoint.layoutmanager.use.tool

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class TouchRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    private var downX = 0f
    private var downY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x
                val moveY = ev.y
                return if (abs(moveX - downX) > abs(moveY - downY)) {
                    downX = moveX
                    downY = moveY
                    super.dispatchTouchEvent(ev)
                } else {
                    downX = moveX
                    downY = moveY
                    true
                }
            }
            MotionEvent.ACTION_UP -> {
                downX = 0f
                downY = 0f
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}