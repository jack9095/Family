package com.xxzy.family.dialog

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import com.mouble.baselibrary.util.LogUtil
import com.xxzy.family.R
import kotlinx.android.synthetic.main.layout_et_dialog.*


/**
 * 软键盘弹框
 */
open class InputDialogFragment : BDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_et_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnKeyListener { _, keyCode, event ->
            val backPressed = event.action == MotionEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK
            Log.e("BaseDialogFragment","keyCode $keyCode  event $event backPressed $backPressed")
            if (backPressed) dismiss()
            backPressed
        }
    }

    override fun onResume() {
        super.onResume()
        //获取当前屏幕内容的高度
        //获取当前屏幕内容的高度
        dialog?.window?.decorView?.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
               override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    //获取View可见区域的bottom
                    val rect = Rect()
                    dialog?.window?.decorView?.getWindowVisibleDisplayFrame(rect)
                    if (bottom != 0 && oldBottom != 0 && bottom - rect.bottom <= 0) {
                        ll_wrap.visibility = View.GONE
                        dismiss()
                        LogUtil.e("dialog 隐藏")
                    } else {
                        ll_wrap.visibility = View.VISIBLE
                        LogUtil.e("dialog 弹出")
                    }
                }
            })

//        writeComment.requestFocus()
    }

    @SuppressLint("ResourceType")
    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    open class Factory {
        open fun build(): InputDialogFragment = InputDialogFragment()
    }
}
