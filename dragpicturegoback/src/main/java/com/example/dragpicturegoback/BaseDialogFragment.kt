package com.example.dragpicturegoback

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

open class BaseDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireActivity(), R.style.Theme_Light_NoTitle_ViewerDialog).apply {
            setCanceledOnTouchOutside(true)
            window?.let(::setWindow)
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.setOnKeyListener { _, keyCode, event ->
            val backPressed = event.action == MotionEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK
            Log.i("BaseDialogFragment","keyCode $keyCode  event $event backPressed $backPressed")
            if (backPressed) onBackPressed()
            backPressed
        }
    }

    override fun onResume() {
        super.onResume()
        view?.requestFocus()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        view?.setOnKeyListener(null)
    }

    open fun setWindow(win: Window) {
        // 给 Window 设置动画
        win.setWindowAnimations(R.style.Animation_Keep)
        // 给 Window 设置内边距
        win.decorView.setPadding(0, 0, 0, 0)
        // 设置属性
        val lp = win.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        win.attributes = lp
        win.setGravity(Gravity.CENTER)
    }

    // 显示 DialogFragment
    fun show(fragmentManager: FragmentManager?) {
        when {
            fragmentManager == null -> showFailure("fragmentManager is detach after parent destroy")
            fragmentManager.isStateSaved -> showFailure("dialog fragment show when fragmentManager isStateSaved")
            else -> show(fragmentManager, javaClass.simpleName)
        }
    }

    open fun showFailure(message: String? = null) {
        Log.i("BaseDialogFragment","show failure $message")
    }

    // 返回的方法
    open fun onBackPressed() {
    }
}