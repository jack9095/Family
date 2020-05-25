package com.xxzy.family.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 圆角的RelativeLayout
 */
//public class RoundRectLayout extends RelativeLayout {
public class RoundRectLayout extends LinearLayout {

    private Path mPath;
    private int mRadius;

    private int mWidth;
    private int mHeight;
    private int mLastRadius; // 最后设置的半径

    public static final int MODE_NONE = 0;
    public static final int MODE_ALL = 1;
    public static final int MODE_LEFT = 2;
    public static final int MODE_TOP = 3;
    public static final int MODE_RIGHT = 4;
    public static final int MODE_BOTTOM = 5;
    public static final int LEFT_RIGHT= 6;

    private int mRoundMode = LEFT_RIGHT;

    public RoundRectLayout(Context context) {
        super(context);
        init();
    }

    public RoundRectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

//        setBackgroundDrawable(new ColorDrawable(0x33ff0000));
        setBackgroundDrawable(new ColorDrawable(0000000000));

        mPath = new Path();
        // 设置填充,用一条直线横贯图形时，外部和内部交替出现
        mPath.setFillType(Path.FillType.EVEN_ODD);

        setCornerRadius(90);
    }

    /**
     * 设置是否圆角裁边
     * @param roundMode
     */
    public void setRoundMode(int roundMode){
        mRoundMode = roundMode;
    }

    /**
     * 设置圆角半径
     * @param radius
     */
    public void setCornerRadius(int radius){
        mRadius = radius;
    }

    private void checkPathChanged(){

        if(getWidth() == mWidth && getHeight() == mHeight && mLastRadius == mRadius){
            return;
        }

        mWidth = getWidth();
        mHeight = getHeight();
        mLastRadius = mRadius;

        // 清除之前绘制的path
        mPath.reset();

        switch (mRoundMode){
            case MODE_ALL:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight), mRadius, mRadius, Path.Direction.CW);
                break;
            case MODE_LEFT:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius},
                        Path.Direction.CW);
                break;
            case MODE_TOP:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{mRadius, mRadius, mRadius, mRadius, 0, 0, 0, 0},
                        Path.Direction.CW);
                break;
            case MODE_RIGHT:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0},
                        Path.Direction.CW);
                break;
            case MODE_BOTTOM:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{0, 0, 0, 0, mRadius, mRadius, mRadius, mRadius},
                        Path.Direction.CW);
                break;
            case LEFT_RIGHT:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius},
                        Path.Direction.CW);
                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {

        if(mRoundMode != MODE_NONE){
            // 把Canvas 的信息保存（画布将当前的状态保存），压入栈
            int saveCount = canvas.save();

            checkPathChanged();
            // 根据路径裁剪
            canvas.clipPath(mPath);
            super.draw(canvas);
            // 画布取出原来所保存的状态，恢复到最近的一个保存点，出栈
//            canvas.restore()
            // 恢复到特定的保存点
            canvas.restoreToCount(saveCount);
        }else {
            super.draw(canvas);
        }
    }
}