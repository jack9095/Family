package com.xxzy.family.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.*
import com.xxzy.family.R
import com.xxzy.family.time.TimeViewListener

class TimeTextView : View {

    private val mRadiusPaint = Paint(Paint.ANTI_ALIAS_FLAG) // 圆角矩形画笔

    private val mTimePaint = Paint(Paint.ANTI_ALIAS_FLAG) // 时间值画笔

    private val mSepPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG) // “:”画笔

    private val mRectRadius = RectF() // 绘制的椭圆矩形

    private var mBaseline = 0f // 基准线

    private var mRadius = 0f // 圆角或者圆的半径

    private var mTimeTextBg = 0 // 倒计时背景色

    private var mBgWidth = 0f // 倒计分秒单个模块背景色宽度

    private var mHWidth = dp2Px(context, 22f) // 倒计时单个模块背景色宽度

    private var mBgHeight = 0f // 倒计时单个模块背景色高度

    private var mSepWidth = 0f // 符号宽度

    private val mLocale = Locale.getDefault()

    private var times: IntArray? = null

    private var countDown: TimeViewListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPaint(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initPaint(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewTimer)
        //背景颜色
        mTimeTextBg = typedArray.getColor(
            R.styleable.TextViewTimer_time_background,
            ContextCompat.getColor(context, R.color.black)
        )
        mBgWidth = typedArray.getDimension(
            R.styleable.TextViewTimer_background_width,
            dp2Px(context, 16f).toFloat()
        )
        mBgHeight = typedArray.getDimension(
            R.styleable.TextViewTimer_background_height,
            dp2Px(context, 16f).toFloat()
        )
        val colonColor = typedArray.getColor(
            R.styleable.TextViewTimer_colon_color,
            ContextCompat.getColor(context, R.color.black)
        )
        val fontColor = typedArray.getColor(
            R.styleable.TextViewTimer_time_color,
            ContextCompat.getColor(context, R.color.white)
        )
        val fontSize = typedArray.getDimension(
            R.styleable.TextViewTimer_time_size,
            dp2Px(getContext(), 12f).toFloat()
        )
        mRadius = typedArray.getDimension(R.styleable.TextViewTimer_radius, 2f)
        mSepWidth = typedArray.getDimension(
            R.styleable.TextViewTimer_sep_width,
            dp2Px(context, 10f).toFloat()
        )

        //设置背景
        mRadiusPaint.run {
            color = mTimeTextBg
            alpha = 180
        }

        //设置字体
        mTimePaint.run {
            typeface = Typeface.DEFAULT
            color = fontColor
            textAlign = Paint.Align.CENTER // 水平居中
            textSize = fontSize
        }

        //设置符号
        mSepPaint.run {
            color = colonColor
            textAlign = Paint.Align.CENTER // 水平居中
            textSize = fontSize
        }
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTimeView(canvas)
    }

    /**
     * @param canvas 绘制倒计时
     */
    private fun drawTimeView(canvas: Canvas) {
        if (times == null) {
            return
        }
        val width = mBgWidth
        var left: Float
        var right: Float

        countDown?.getTextStyle(times)?.let {
            val timeViewWidth = calculateTimeViewWidth(it, width, mSepWidth)
            left =
                if ((getWidth() - timeViewWidth) / 2 < 0) 0f else (getWidth() - timeViewWidth) / 2 + dp2Px(
                    context,
                    3f
                ).toFloat() // 初始时绘制时间的起始位置
            right = left + width // 初始时右侧位置
            for (i in it.indices) {
                if (i % 2 != 0) {
                    val textWidth = mSepPaint.measureText(it[i])
                    left += textWidth / 2 + mSepWidth / 2
                    canvas.drawText(it[i], left, mBaseline, mSepPaint) // 绘制冒号 ：
                    left += textWidth / 2 + mSepWidth / 2
                    right += mSepWidth + textWidth
                } else {
                    val timeStr = fixTimeString(it[i])
                    //绘制背景色
                    mRadiusPaint.color = mTimeTextBg

                    if (timeStr.length >= 3) {
                        drawRoundCircleBg(canvas, left + mBgWidth - mHWidth, right, mBgHeight)
                    } else {
                        drawRoundCircleBg(canvas, left, right, mBgHeight)
                    }

                    //绘制时间值
                    drawTextTimeView(canvas, timeStr)

                    // 重新计算位置
                    left += width
                    right += width
                }
            }
        }
    }

    /**
     * 绘制时间视图
     *
     * @param canvas  Canvas
     * @param timeStr 时间字符串
     */
    private fun drawTextTimeView(canvas: Canvas, timeStr: String) {
        initFontBaseLine()
        // 时间值的绘制
        canvas.drawText(timeStr, mRectRadius.centerX(), mBaseline, mTimePaint)
    }

    /**
     * 个位数时，直接补0，如'03'
     *
     * @param time 时间
     * @return 返回填充后的时间字符串
     */
    private fun fixTimeString(time: String): String {
        val timeCode: Int = try {
            time.toInt()
        } catch (e: Exception) {
            0
        }
        return if (timeCode <= 9) {
            String.format(mLocale, "%1\$d%2\$d", 0, timeCode)
        } else String.format(mLocale, "%1\$d", timeCode)
    }

    /**
     * 计算时间所占的宽度
     *
     * @param drawItemArr 需要绘制的内容
     * @param width       一个倒计时单元所占的宽度  如 04:11:43  43所占的宽度
     * @param sepWidth    分隔符所占的宽度
     */
    private fun calculateTimeViewWidth(
        drawItemArr: Array<String>,
        width: Float,
        sepWidth: Float
    ): Float {
        var timeViewWidth = 0f
        for (i in drawItemArr.indices) { // 计算绘制需要占的宽度
            timeViewWidth += if (i % 2 != 0) {
                sepWidth + mSepPaint.measureText(drawItemArr[i])
            } else {
                width
            }
        }
        return timeViewWidth
    }

    /**
     * 设置要显示倒计时的格式
     */
    fun setListener(listener: TimeViewListener?) {
        countDown = listener
    }

    /**
     * 刷新时间显示
     * @param times 时间集合
     */
    fun refreshTime(times: IntArray?) {
        this.times = times
        invalidate()
    }

    /**
     * 初始化基准线
     */
    private fun initFontBaseLine() {
        if (mBaseline < 0.01) {
            val fontMetrics = mTimePaint.fontMetricsInt
            mBaseline =
                (mBgHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top // 垂直居中
        }
    }

    /**
     * 绘制圆角矩形背景
     *
     * @param canvas Canvas
     * @param left   左边宽度
     * @param right  右边宽度
     * @param bottom 底部高度
     */
    private fun drawRoundCircleBg(canvas: Canvas, left: Float, right: Float, bottom: Float) {
        mRectRadius[left, 0f, right] = bottom
        canvas.drawRoundRect(mRectRadius, mRadius, mRadius, mRadiusPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = dp2Px(context, 16f)
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = dp2Px(context, 84f)
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    private fun dp2Px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}