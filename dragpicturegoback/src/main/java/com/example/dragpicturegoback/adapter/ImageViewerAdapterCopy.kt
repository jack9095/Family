package com.example.dragpicturegoback.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import com.example.dragpicturegoback.ImageViewerAdapterListener
import com.example.dragpicturegoback.R
import com.example.dragpicturegoback.bean.ItemBean
import com.example.dragpicturegoback.utils.inflate
import com.example.dragpicturegoback.viewholders.PhotoViewHolder
import com.example.dragpicturegoback.viewholders.SubsamplingViewHolder
import com.example.dragpicturegoback.viewholders.UnknownViewHolder
import java.util.*

/**
 * 查看大图 ViewPage2 的适配器
 */
class ImageViewerAdapterCopy(initKey: Long) : PagedListAdapter<ItemBean, RecyclerView.ViewHolder>(diff) {
    private var listener: ImageViewerAdapterListener? = null
    private var key = initKey

    fun setListener(callback: ImageViewerAdapterListener?) {
        listener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.PHOTO -> PhotoViewHolder(parent.inflate(R.layout.item_imageviewer_photoview), callback)
            ItemType.SUBSAMPLING -> SubsamplingViewHolder(parent.inflate(R.layout.item_imageviewer_subsampling), callback)
            else -> UnknownViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        Log.e("ImageViewerAdapter","onBindViewHolder $key $position $item")
        when (holder) {
            is PhotoViewHolder -> item?.let { holder.bind(it) }
            is SubsamplingViewHolder -> item?.let { holder.bind(it) }
        }

        if (item?.id == key) {
            listener?.onInit(holder)
            key = NO_ID
        }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.id ?: NO_ID
    override fun getItemViewType(position: Int) = getItem(position)?.type ?: ItemType.UNKNOWN

    private val callback: ImageViewerAdapterListener = object : ImageViewerAdapterListener {
        override fun onInit(viewHolder: RecyclerView.ViewHolder) {
            listener?.onInit(viewHolder)
        }

        override fun onDrag(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float) {
            listener?.onDrag(viewHolder, view, fraction)
        }

        override fun onRelease(viewHolder: RecyclerView.ViewHolder, view: View) {
            listener?.onRelease(viewHolder, view)
        }

        override fun onRestore(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float) {
            listener?.onRestore(viewHolder, view, fraction)
        }
    }
}

// 计算数据差量的工具，避免无脑调用 notifyDataSetChanged
private val diff = object : DiffUtil.ItemCallback<ItemBean>() {
    override fun areItemsTheSame(oldItem: ItemBean, newItem: ItemBean): Boolean {
        return newItem.type == oldItem.type && newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: ItemBean, newItem: ItemBean): Boolean {
        return newItem.type == oldItem.type && newItem.id == oldItem.id
                && Objects.equals(newItem, oldItem)
    }
}
