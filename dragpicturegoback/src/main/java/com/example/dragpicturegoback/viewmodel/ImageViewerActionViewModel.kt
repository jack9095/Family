package com.example.dragpicturegoback.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object ViewerActions {
    const val SET_CURRENT_ITEM = "setCurrentItem"
    const val DISMISS = "dismiss"
}

class ImageViewerActionViewModel : ViewModel() {

    val actionEvent = MutableLiveData<Pair<String, Any?>>()

    // 设置点击上一页、下一页的
    fun setCurrentItem(pos: Int) = internalHandle(ViewerActions.SET_CURRENT_ITEM, pos)

    // 点击大图弹窗消失的方法
    fun dismiss() = internalHandle(ViewerActions.DISMISS, null)

    private fun internalHandle(action: String, extra: Any?) {
        actionEvent.value = Pair(action, extra)
        actionEvent.value = null
    }
}