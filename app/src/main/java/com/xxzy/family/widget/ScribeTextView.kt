package com.xxzy.family.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScribeTextView: AppCompatTextView {

    constructor(context: Context):super(context){
        init(true)
    }

    constructor(context: Context,attrs: AttributeSet): super(context,attrs){
        init(true)
    }

    constructor(context: Context,attrs: AttributeSet,defStyleAttr: Int): super(context,attrs,defStyleAttr){
        init(true)
    }

    fun init(bool: Boolean){
        if (bool) { // 中间加横线
            paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //  getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            // 取消划线
            // getPaint().setFlags(0);
        } else { // 底部加横线
            paint.flags = Paint.UNDERLINE_TEXT_FLAG
        }
    }
}