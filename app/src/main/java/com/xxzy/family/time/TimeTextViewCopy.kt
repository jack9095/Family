package com.xxzy.family.time

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.xxzy.family.R
import java.util.*

class TimeTextViewCopy: View {

    private val mRadiusPaint = Paint(Paint.ANTI_ALIAS_FLAG) // 圆角矩形画笔

    private val mTimePaint = Paint(Paint.ANTI_ALIAS_FLAG) // 时间值画笔

    private val mSepPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG) // “:”画笔

    private val mRectRadius = RectF() // 绘制的椭圆矩形

    private var mBaseline = 0f // 基准线

    private var mRadius = 0f // 圆角或者圆的半径

    private var mTimeTextBg = 0 // 倒计时背景色

    private var mBgWidth = 0f // 倒计分秒单个模块背景色宽度

    private var mHWidth = ToolSize.dp2Px(context,22f) // 倒计时单个模块背景色宽度

    private var mBgHeight = 0f // 倒计时单个模块背景色高度

    private var mSepWidth = 0f // 符号宽度

    private val mLocale = Locale.getDefault()

    private var times: IntArray? = null

    private var countDown: TimeViewListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context,attrs: AttributeSet) : super(context, attrs) {
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
            ToolSize.dp2Px(context, 16f).toFloat()
        )
        mBgHeight = typedArray.getDimension(
            R.styleable.TextViewTimer_background_height,
            ToolSize.dp2Px(context, 16f).toFloat()
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
            ToolSize.dp2Px(getContext(), 12f).toFloat()
        )
        mRadius = typedArray.getDimension(R.styleable.TextViewTimer_radius, 2f)
        mSepWidth = typedArray.getDimension(
            R.styleable.TextViewTimer_sep_width,
            ToolSize.dp2Px(context, 10f).toFloat()
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
        var left = 0f
        var right: Float

        countDown?.getTextStyle(times)?.let {
            val timeViewWidth = calculateTimeViewWidth(it, width, mSepWidth)
            Log.e("测试 timeViewWidth = ",timeViewWidth.toString())
            Log.e("测试 getWidth() = ",getWidth().toString())
            Log.e("测试 数组大小 = ",it.size.toString())
            left = if ((getWidth() - timeViewWidth) / 2 < 0) 0f else (getWidth() - timeViewWidth) / 2 // 初始时绘制时间的起始位置
            right = left + width // 初始时右侧位置
            for (i in it.indices) {
                Log.e("测试 角标 = ",i.toString())
                if (i % 2 != 0) {
                    val textWidth = mSepPaint.measureText(it[i])
                    left += textWidth / 2 + mSepWidth / 2
                    canvas.drawText(it[i], left, mBaseline, mSepPaint) // 绘制冒号 ：
                    left += textWidth / 2 + mSepWidth / 2
                    right += mSepWidth + textWidth
                    Log.e("测试 绘制冒号的次数 ","it.size.toString()")
                } else {
                    val timeStr = fixTimeString(it[i])
                    //绘制背景色
                    mRadiusPaint.color = mTimeTextBg

                    if (i == 0) {
                        drawRoundCircleBg(canvas, left + mBgWidth - mHWidth, right, mBgHeight)
                    } else {
                        drawRoundCircleBg(canvas, left, right, mBgHeight)
                    }

                    //绘制时间值
                    drawTextTimeView(canvas, left + mRadius, timeStr)

                    // 重新计算位置
//                    if (i == 0) {
//                        left += mHWidth
//                        right += mHWidth
//                    } else {

//                    if (i != it.size - 1) {
                        left += width
                        right += width
//                    }

//                    }
                }
            }
        }
    }

    /**
     * 绘制时间视图
     *
     * @param canvas  Canvas
     * @param x       圆形背景时圆心的X坐标
     * @param timeStr 时间字符串
     */
    private fun drawTextTimeView(canvas: Canvas, x: Float, timeStr: String) {
        initFontBaseLine()
        //圆角矩形背景
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
     * 刷新时间显示
     * @param times 时间集合
     */
    fun refreshTime(times: IntArray?) {
        this.times = times
        invalidate()
    }

    /**
     * 计算时间所占的宽度
     *
     * @param drawItemArr 需要绘制的内容
     * @param width       一个倒计时单元所占的宽度  如 04:11:43  43所占的宽度
     * @param sepWidth    分隔符所占的宽度
     */
    private fun calculateTimeViewWidth(drawItemArr: Array<String>, width: Float, sepWidth: Float): Float {
        var timeViewWidth = 0f
        for (i in drawItemArr.indices) { // 计算绘制需要占的宽度
            timeViewWidth += if (i % 2 != 0) {
                sepWidth + mSepPaint.measureText(drawItemArr[i])
            } else {
                width
//                if (i == 0) mHWidth.toFloat() else width
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
     * 设置小时出现三位数的时候背景的宽度 22f
     */
    fun hourBgWidth(value: Float) {
        mHWidth = ToolSize.dp2Px(context, value)
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
            heightSize = ToolSize.dp2Px(context,16f)
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = ToolSize.dp2Px(context,100f)
        }
        setMeasuredDimension(widthSize, heightSize)
    }
}