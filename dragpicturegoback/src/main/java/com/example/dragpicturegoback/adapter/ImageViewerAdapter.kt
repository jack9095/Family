package com.example.dragpicturegoback.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import com.example.dragpicturegoback.ImageViewerAdapterListener
import com.example.dragpicturegoback.R
import com.example.dragpicturegoback.bean.ItemBean
import com.example.dragpicturegoback.utils.inflate
import com.example.dragpicturegoback.viewholders.PhotoViewHolder
import com.example.dragpicturegoback.viewholders.SubsamplingViewHolder
import com.example.dragpicturegoback.viewholders.UnknownViewHolder

/**
 * 查看大图 ViewPage2 的适配器
 */
class ImageViewerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: ImageViewerAdapterListener? = null
    private var key: Long = 0 // 就是适配器数据类中的 id （点击哪个item，传进来就是哪个 item 的 id）
    private var lists: MutableList<out ItemBean>? = null

    fun setListener(callback: ImageViewerAdapterListener?) {
        listener = callback
    }

    fun setData(lists: MutableList<out ItemBean>?,initKey: Long){
        this.lists = lists
        this.key = initKey
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.PHOTO -> PhotoViewHolder(parent.inflate(R.layout.item_imageviewer_photoview), callback)
            ItemType.SUBSAMPLING -> SubsamplingViewHolder(parent.inflate(R.layout.item_imageviewer_subsampling), callback)
            else -> UnknownViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = lists?.get(position)
        when (holder) {
            is PhotoViewHolder -> item?.let { holder.bind(it) }
            is SubsamplingViewHolder -> item?.let { holder.bind(it) }
        }

        // 初始化 adapter监听的回调方法
        if (item?.id == key) {
            listener?.onInit(holder)
            key = NO_ID
        }
    }

    override fun getItemCount(): Int = lists?.size ?: 0
    override fun getItemId(position: Int): Long = lists?.get(position)?.id ?: NO_ID
    override fun getItemViewType(position: Int) = lists?.get(position)?.type ?: ItemType.UNKNOWN

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

        override fun onClick(viewHolder: RecyclerView.ViewHolder, view: View) {
            listener?.onClick(viewHolder, view)
        }

        override fun onRestore(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float) {
            listener?.onRestore(viewHolder, view, fraction)
        }
    }

}
