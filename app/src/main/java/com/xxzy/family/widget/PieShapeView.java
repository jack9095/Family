package com.xxzy.family.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xxzy.family.data.PieData;

import java.util.ArrayList;
import java.util.List;

public class PieShapeView extends View {

    // 创建画笔
    Paint paint;

    int defaultColor = Color.YELLOW;

    private List<PieData> lists = new ArrayList<>();


    public PieShapeView(Context context) {
        super(context);
        init();
    }

    public PieShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PieShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    float sumValue; // 计算数值总和
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        lists.add(new PieData(Color.RED, 0, 60));
        lists.add(new PieData(Color.GREEN, 60, 70));
        lists.add(new PieData(Color.BLUE, 170, 100));
        lists.add(new PieData(Color.BLACK, 270, 90));

        for (int i = 0; i < lists.size(); i++) {
            sumValue += lists.get(i).end; // 总数
        }
        Log.e("总数= ",sumValue + "");

        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).start = (lists.get(i).end / sumValue) * 360; // 对应的角度
            Log.e("角度2= ",lists.get(i).end + "");
            Log.e("角度1= ",lists.get(i).end / sumValue + "");
            Log.e("角度= ",(lists.get(i).end / sumValue) * 360 + "");
        }

//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setChange();
//            }
//        },1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        switch (modeWidth) {
            case MeasureSpec.AT_MOST:  // 父控件不限制子控件的大小
                sizeWidth = 800;
                break;
            case MeasureSpec.EXACTLY:  // 确定大小
                sizeWidth = sizeWidth;
                break;
            case MeasureSpec.UNSPECIFIED:  // 不做任何约束，这个一般用于

                break;
        }

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        switch (modeHeight) {
            case MeasureSpec.AT_MOST:  // 父控件不限制子控件的大小
                sizeHeight = 800;
                break;
            case MeasureSpec.EXACTLY:  // 确定大小
                sizeHeight = sizeHeight;
                break;
            case MeasureSpec.UNSPECIFIED:  // 不做任何约束，这个一般用于

                break;
        }

        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 矩形范围
        RectF rectF = new RectF(100, 100, 400, 400);

        /*
         * 参数1，绘制范围
         * 参数2，绘制扇形起点
         * 参数3，绘制扇形角度
         * 参数4，是否绘制中心点
         * 参数5，绘制使用的画笔
         */
//       canvas.drawArc(rectF,0,90,true,paint);

        for (int i = 0; i < lists.size(); i++) {
            paint.setColor(lists.get(i).color);
            Log.e("PieShapeView","currentAngle = " + currentAngle + "  ----->  end" + lists.get(i).start);
            canvas.drawArc(rectF, currentAngle, lists.get(i).start, true, paint);
            currentAngle += lists.get(i).start;
        }

//        canvas.save();
//        canvas.restore();
    }

    float start = 0f;
    float end = 90f;
    int color = defaultColor;
    float currentAngle;
    public void setChange() {
        for (int i = 0; i < lists.size(); i++) {
            color = lists.get(i).color;
            start = lists.get(i).start;
            end = lists.get(i).end;
            invalidate();
        }
    }

}
