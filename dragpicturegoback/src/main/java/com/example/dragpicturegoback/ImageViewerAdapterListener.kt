package com.example.dragpicturegoback

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 图片适配器的接口监听回调
 */
interface ImageViewerAdapterListener {
    // 初始化方法
    fun onInit(viewHolder: RecyclerView.ViewHolder)
    fun onDrag(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float)
    fun onRestore(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float)
    fun onRelease(viewHolder: RecyclerView.ViewHolder, view: View)
}