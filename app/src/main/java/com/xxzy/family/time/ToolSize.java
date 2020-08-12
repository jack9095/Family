package com.xxzy.family.time;

import android.content.Context;



public class ToolSize {
    /**
     * 将dp值转换为px值
     *
     * @param context context
     * @param dpValue dp值
     * @return 转化后的px值
     */
   public static int dp2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
