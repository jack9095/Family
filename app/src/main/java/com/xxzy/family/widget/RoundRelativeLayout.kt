package com.xxzy.family.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout

class RoundRelativeLayout : RelativeLayout {

    var mWidth = 0
    var mHeight = 0
    var mLastRadius = 0 // 最后设置的半径
    var mRadius = 30
    val MODE_NONE = 0
    val MODE_ALL = 1
    val MODE_LEFT = 2
    val MODE_TOP = 3
    val MODE_RIGHT = 4
    val MODE_BOTTOM = 5

    private var mRoundMode = MODE_ALL
    lateinit var mPath: Path

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, atts: AttributeSet) : super(context, atts) {
        init()
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun init() {
        setBackgroundDrawable(ColorDrawable(0x33ff0000));
        mPath = Path()
        mPath.fillType = Path.FillType.EVEN_ODD // 设置填充,用一条直线横贯图形时，外部和内部交替出现
        setCornerRadius(60)
    }

    /**
     * 设置是否圆角裁边
     */
    fun setRoundMode(roundMode: Int) {
        mRoundMode = roundMode
    }

    // 设置圆角半径
    fun setCornerRadius(radius: Int) {
        mRadius = radius
    }

    // 改变路径的绘制
    fun checkPathChanged() {

        if(width == mWidth && height == mHeight && mLastRadius == mRadius){
            return
        }

        mWidth = width
        mHeight = height
        mLastRadius = mRadius

        mPath.reset() // 清除之前绘制的path

        when (mRoundMode) {
            MODE_ALL -> { // 四边全部圆角
                mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    mRadius.toFloat(),
                    mRadius.toFloat(),
                    Path.Direction.CW
                )
            }
            MODE_LEFT -> { // 左边圆角
                mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(mRadius.toFloat(), mRadius.toFloat(), 0f, 0f, 0f, 0f, mRadius.toFloat(), mRadius.toFloat()),
                    Path.Direction.CW
                )
            }
            MODE_TOP -> { // 上边圆角
                Log.e("日志","上边圆角")
                mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), 0f, 0f, 0f, 0f),
                    Path.Direction.CW
                )
            }
            MODE_RIGHT -> { // 右边圆角
                mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(0f, 0f, mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), 0f, 0f),
                    Path.Direction.CW
                )
            }
            MODE_BOTTOM -> { // 下边圆角
                mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(0f, 0f, 0f, 0f, mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat()),
                    Path.Direction.CW
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {

        if (mRoundMode != MODE_NONE) {
            val saveCount = canvas.save()
            checkPathChanged()
            canvas.clipPath(mPath)
            super.draw(canvas)
            canvas.restoreToCount(saveCount)
        } else {
            super.draw(canvas)
        }

//        if (mRoundMode != MODE_NONE) {
//            Log.e("RoundRelativeLayout","绘制")
//            // 把Canvas 的信息保存（画布将当前的状态保存），压入栈
//            val saveCanvas = canvas.save()
//            checkPathChanged()
//            // 根据路径裁剪
//            canvas.clipPath(mPath)
//            super.onDraw(canvas)
//            // 画布取出原来所保存的状态，恢复到最近的一个保存点，出栈
////            canvas.restore()
//            // 恢复到特定的保存点
//            canvas.restoreToCount(saveCanvas)
//        } else {
//            super.onDraw(canvas)
//        }
    }
}