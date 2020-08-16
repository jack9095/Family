package com.maxxipoint.layoutmanager.use.tool

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.maxxipoint.layoutmanager.R
import kotlin.math.abs

class ShadowLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var clickAbleFalseDrawable: Drawable? = null
    private var clickAbleFalseColor = -1

    private var layoutBackground: Drawable? = null
    private var layoutBackgroundTrue: Drawable? = null

    private var mBackGroundColor = 0
    private var mBackGroundColorTrue = -1
    private var mShadowColor = 0
    private var mShadowLimit = 0f
    private var mCornerRadius = 0f
    private var mDx = 0f
    private var mDy = 0f
    private var leftShow = false
    private var rightShow = false
    private var topShow = false
    private var bottomShow = false
    private var shadowPaint = Paint()
    private var paint = Paint()

    private var leftPadding = 0
    private var topPadding = 0
    private var rightPadding = 0
    private var bottomPadding = 0

    private var rectF = RectF()

    private var selectorType = 1
    private var isShowShadow = true
    private var isSym = false

    private var mCornerRadiusLeftTop = 0f
    private var mCornerRadiusRightTop = 0f
    private var mCornerRadiusLeftBottom = 0f
    private var mCornerRadiusRightBottom = 0f

    private var paintStroke = Paint()
    private var strokeWith = 0f
    private var strokeColor = 0
    private var strokeColorTrue = 0

    init {
        initView(attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) setBackgroundCompat(w, h)
    }

    private fun initView(attrs: AttributeSet) {
        initAttributes(attrs)
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
        paintStroke.isAntiAlias = true
        paintStroke.style = Paint.Style.STROKE
        paintStroke.strokeWidth = strokeWith
        if (strokeColor != -1) {
            paintStroke.color = strokeColor
        }

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = mBackGroundColor
        setPadding()
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    private fun setPadding() {
        if (isShowShadow && mShadowLimit > 0) {
            if (isSym) {
                val xPadding = (mShadowLimit + abs(mDx)).toInt()
                val yPadding = (mShadowLimit + abs(mDy)).toInt()
                leftPadding = if (leftShow) {
                    xPadding
                } else {
                    0
                }
                topPadding = if (topShow) {
                    yPadding
                } else {
                    0
                }
                rightPadding = if (rightShow) {
                    xPadding
                } else {
                    0
                }
                bottomPadding = if (bottomShow) {
                    yPadding
                } else {
                    0
                }
            } else {
                if (abs(mDy) > mShadowLimit) {
                    mDy = if (mDy > 0) {
                        mShadowLimit
                    } else {
                        0 - mShadowLimit
                    }
                }
                if (abs(mDx) > mShadowLimit) {
                    mDx = if (mDx > 0) {
                        mShadowLimit
                    } else {
                        0 - mShadowLimit
                    }
                }
                topPadding = if (topShow) {
                    (mShadowLimit - mDy).toInt()
                } else {
                    0
                }
                bottomPadding = if (bottomShow) {
                    (mShadowLimit + mDy).toInt()
                } else {
                    0
                }
                rightPadding = if (rightShow) {
                    (mShadowLimit - mDx).toInt()
                } else {
                    0
                }
                leftPadding = if (leftShow) {
                    (mShadowLimit + mDx).toInt()
                } else {
                    0
                }
            }
            setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setBackgroundCompat(w: Int, h: Int) {
        if (isShowShadow) {
            isAddAlpha(mShadowColor)
            val bitmap = createShadowBitmap(
                w,
                h,
                mCornerRadius,
                mShadowLimit,
                mDx,
                mDy,
                mShadowColor
            )
            background = BitmapDrawable(resources, bitmap)
        } else {
            setBackgroundColor(Color.parseColor("#00000000"))
        }
    }

    @SuppressLint("Recycle")
    private fun initAttributes(attrs: AttributeSet) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout) ?: return
        try {
            isShowShadow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHidden, false)
            leftShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenLeft, false)
            rightShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenRight, false)
            bottomShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenBottom, false)
            topShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenTop, false)
            mCornerRadius = attr.getDimension(
                R.styleable.ShadowLayout_hl_cornerRadius,
                resources.getDimension(R.dimen.dp_0)
            )
            mCornerRadiusLeftTop =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftTop, -1f)
            mCornerRadiusLeftBottom =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftBottom, -1f)
            mCornerRadiusRightTop =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightTop, -1f)
            mCornerRadiusRightBottom =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightBottom, -1f)

            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, 0f)
            if (mShadowLimit == 0f) {
                isShowShadow = false
            }

            mDx = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0f)
            mDy = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0f)
            mShadowColor = attr.getColor(
                R.styleable.ShadowLayout_hl_shadowColor,
                ContextCompat.getColor(context, R.color.default_shadow_color)
            )
            selectorType = attr.getInt(R.styleable.ShadowLayout_hl_shapeMode, 1)
            isSym = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowSymmetry, true)

            mBackGroundColor = ContextCompat.getColor(context, R.color.default_shadowback_color)
            val background =
                attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground)
            if (background != null) {
                if (background is ColorDrawable) {
                    mBackGroundColor = background.color
                } else {
                    layoutBackground = background
                }
            }
            val trueBackground =
                attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground_true)
            if (trueBackground != null) {
                if (trueBackground is ColorDrawable) {
                    mBackGroundColorTrue = trueBackground.color
                } else {
                    layoutBackgroundTrue = trueBackground
                }
            }
            if (mBackGroundColorTrue != -1 && layoutBackground != null) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置颜色时，必须保持都为颜色")
            }
            if (layoutBackground == null && layoutBackgroundTrue != null) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置图片时，必须保持都为图片")
            }
            strokeColor = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor, -1)
            strokeColorTrue = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor_true, -1)
            if (strokeColor == -1 && strokeColorTrue != -1) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_strokeColor_true属性，必须先设置ShadowLayout_hl_strokeColor属性")
            }
            strokeWith =
                attr.getDimension(R.styleable.ShadowLayout_hl_strokeWith, dip2px(1f).toFloat())
            if (strokeWith > dip2px(7f)) {
                strokeWith = dip2px(5f).toFloat()
            }
            val clickAbleFalseBackground =
                attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackgroundClickableFalse)
            if (clickAbleFalseBackground != null) {
                if (clickAbleFalseBackground is ColorDrawable) {
                    clickAbleFalseColor = clickAbleFalseBackground.color
                } else {
                    clickAbleFalseDrawable = clickAbleFalseBackground
                }
            }
            isClickable = true
        } finally {
            attr.recycle()
        }
    }

    private fun createShadowBitmap(
        shadowWidthP: Int, shadowHeightP: Int, cornerRadiusP: Float, shadowRadiusP: Float,
        dxP: Float, dyP: Float, shadowColor: Int
    ): Bitmap {
        var shadowWidth = shadowWidthP
        var shadowHeight = shadowHeightP
        var cornerRadius = cornerRadiusP
        var shadowRadius = shadowRadiusP
        var dx = dxP
        var dy = dyP
        dx /= 4
        dy /= 4
        shadowWidth /= 4
        shadowHeight /= 4
        cornerRadius /= 4
        shadowRadius /= 4
        val output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val shadowRect = RectF(
            shadowRadius,
            shadowRadius,
            shadowWidth - shadowRadius,
            shadowHeight - shadowRadius
        )
        if (isSym) {
            if (dy > 0) {
                shadowRect.top += dy
                shadowRect.bottom -= dy
            } else if (dy < 0) {
                shadowRect.top += abs(dy)
                shadowRect.bottom -= abs(dy)
            }
            if (dx > 0) {
                shadowRect.left += dx
                shadowRect.right -= dx
            } else if (dx < 0) {
                shadowRect.left += abs(dx)
                shadowRect.right -= abs(dx)
            }
        } else {
            shadowRect.top -= dy
            shadowRect.bottom -= dy
            shadowRect.right -= dx
            shadowRect.left -= dx
        }
        shadowPaint.color = Color.TRANSPARENT
        if (!isInEditMode) { //dx  dy
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor)
        }
        if (mCornerRadiusLeftBottom == -1f && mCornerRadiusLeftTop == -1f && mCornerRadiusRightTop == -1f && mCornerRadiusRightBottom == -1f) {
            canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)
        } else {
            rectF.left = leftPadding.toFloat()
            rectF.top = topPadding.toFloat()
            rectF.right = width - rightPadding.toFloat()
            rectF.bottom = height - bottomPadding.toFloat()
            shadowPaint.isAntiAlias = true
            val leftTop: Int = if (mCornerRadiusLeftTop == -1f) {
                mCornerRadius.toInt() / 4
            } else {
                mCornerRadiusLeftTop.toInt() / 4
            }
            val leftBottom: Int = if (mCornerRadiusLeftBottom == -1f) {
                mCornerRadius.toInt() / 4
            } else {
                mCornerRadiusLeftBottom.toInt() / 4
            }
            val rightTop: Int = if (mCornerRadiusRightTop == -1f) {
                mCornerRadius.toInt() / 4
            } else {
                mCornerRadiusRightTop.toInt() / 4
            }
            val rightBottom: Int = if (mCornerRadiusRightBottom == -1f) {
                mCornerRadius.toInt() / 4
            } else {
                mCornerRadiusRightBottom.toInt() / 4
            }
            val outerR = floatArrayOf(
                leftTop.toFloat(),
                leftTop.toFloat(),
                rightTop.toFloat(),
                rightTop.toFloat(),
                rightBottom.toFloat(),
                rightBottom.toFloat(),
                leftBottom.toFloat(),
                leftBottom.toFloat()
            )
            val path = Path()
            path.addRoundRect(shadowRect, outerR, Path.Direction.CW)
            canvas.drawPath(path, shadowPaint)
        }
        return output
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectF.left = leftPadding.toFloat()
        rectF.top = topPadding.toFloat()
        rectF.right = width - rightPadding.toFloat()
        rectF.bottom = height - bottomPadding.toFloat()
        val trueHeight = (rectF.bottom - rectF.top).toInt()
        if (getChildAt(0) != null) {
            if (mCornerRadiusLeftTop == -1f && mCornerRadiusLeftBottom == -1f && mCornerRadiusRightTop == -1f && mCornerRadiusRightBottom == -1f) {
                if (mCornerRadius > trueHeight / 2) {
                    canvas.drawRoundRect(
                        rectF,
                        trueHeight / 2.toFloat(),
                        trueHeight / 2.toFloat(),
                        paint
                    )
                    if (strokeColor != -1) {
                        val rectFStroke = RectF(
                            rectF.left + strokeWith / 2,
                            rectF.top + strokeWith / 2,
                            rectF.right - strokeWith / 2,
                            rectF.bottom - strokeWith / 2
                        )
                        canvas.drawRoundRect(
                            rectFStroke,
                            trueHeight / 2.toFloat(),
                            trueHeight / 2.toFloat(),
                            paintStroke
                        )
                    }
                } else {
                    canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint)
                    if (strokeColor != -1) {
                        val rectFStroke = RectF(
                            rectF.left + strokeWith / 2,
                            rectF.top + strokeWith / 2,
                            rectF.right - strokeWith / 2,
                            rectF.bottom - strokeWith / 2
                        )
                        canvas.drawRoundRect(
                            rectFStroke,
                            mCornerRadius,
                            mCornerRadius,
                            paintStroke
                        )
                    }
                }
            } else {
                setSpaceCorner(canvas, trueHeight)
            }
        }
    }

    private fun setSpaceCorner(canvas: Canvas, trueHeight: Int) {
        var leftTop: Int
        var rightTop: Int
        var rightBottom: Int
        var leftBottom: Int
        leftTop = if (mCornerRadiusLeftTop == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusLeftTop.toInt()
        }
        if (leftTop > trueHeight / 2) {
            leftTop = trueHeight / 2
        }
        rightTop = if (mCornerRadiusRightTop == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusRightTop.toInt()
        }
        if (rightTop > trueHeight / 2) {
            rightTop = trueHeight / 2
        }
        rightBottom = if (mCornerRadiusRightBottom == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusRightBottom.toInt()
        }
        if (rightBottom > trueHeight / 2) {
            rightBottom = trueHeight / 2
        }
        leftBottom = if (mCornerRadiusLeftBottom == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusLeftBottom.toInt()
        }
        if (leftBottom > trueHeight / 2) {
            leftBottom = trueHeight / 2
        }
        val outerR = floatArrayOf(
            leftTop.toFloat(),
            leftTop.toFloat(),
            rightTop.toFloat(),
            rightTop.toFloat(),
            rightBottom.toFloat(),
            rightBottom.toFloat(),
            leftBottom.toFloat(),
            leftBottom.toFloat()
        )
        if (strokeColor != -1) {
            val mDrawables = ShapeDrawable(RoundRectShape(outerR, null, null))
            mDrawables.paint.color = paint.color
            mDrawables.setBounds(
                leftPadding,
                topPadding,
                width - rightPadding,
                height - bottomPadding
            )
            mDrawables.draw(canvas)
            val mDrawablesStroke = ShapeDrawable(RoundRectShape(outerR, null, null))
            mDrawablesStroke.paint.color = paintStroke.color
            mDrawablesStroke.paint.style = Paint.Style.STROKE
            mDrawablesStroke.paint.strokeWidth = strokeWith
            mDrawablesStroke.setBounds(
                (leftPadding + strokeWith / 2).toInt(),
                (topPadding + strokeWith / 2).toInt(),
                (width - rightPadding - strokeWith / 2).toInt(),
                (height - bottomPadding - strokeWith / 2).toInt()
            )
            mDrawablesStroke.draw(canvas)
        } else {
            val mDrawables = ShapeDrawable(RoundRectShape(outerR, null, null))
            mDrawables.paint.color = paint.color
            mDrawables.setBounds(
                leftPadding,
                topPadding,
                width - rightPadding,
                height - bottomPadding
            )
            mDrawables.draw(canvas)
        }
    }

    private fun isAddAlpha(color: Int) {
        if (Color.alpha(color) == 255) {
            var red = Integer.toHexString(Color.red(color))
            var green = Integer.toHexString(Color.green(color))
            var blue = Integer.toHexString(Color.blue(color))
            if (red.length == 1) red = "0$red"
            if (green.length == 1) green = "0$green"
            if (blue.length == 1) blue = "0$blue"
            val endColor = "#2a$red$green$blue"
            mShadowColor = convertToColorInt(endColor)
        }
    }

    private fun convertToColorInt(argb: String): Int {
        var arg = argb
        if (!argb.startsWith("#")) arg = "#$argb"
        return Color.parseColor(arg)
    }
}