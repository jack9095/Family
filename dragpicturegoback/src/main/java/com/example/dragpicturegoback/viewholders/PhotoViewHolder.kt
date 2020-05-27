package com.example.dragpicturegoback.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dragpicturegoback.ImageViewerAdapterListener
import com.example.dragpicturegoback.R
import com.example.dragpicturegoback.bean.ItemBean
import com.example.dragpicturegoback.utils.loadImage
import com.example.dragpicturegoback.widgets.PhotoView2
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_imageviewer_photoview.*

/**
 * 实现了 LayoutContainer 接口，就可以使用 kotlin 中自动实现 findViewById 的功能，导入布局就好了
 */
class PhotoViewHolder(override val containerView: View, callback: ImageViewerAdapterListener) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    init {
        photoView.setListener(object : PhotoView2.Listener {
            override fun onDrag(view: PhotoView2, fraction: Float) {
                callback.onDrag(this@PhotoViewHolder, view, fraction)
            }
            override fun onRestore(view: PhotoView2, fraction: Float) {
                callback.onRestore(this@PhotoViewHolder, view, fraction)
            }
            override fun onRelease(view: PhotoView2) = callback.onRelease(this@PhotoViewHolder, view)
        })

        photoView.setOnClickListener{
            callback.onClick(this@PhotoViewHolder,photoView)
        }
    }

    fun bind(item: ItemBean) {
        photoView.setTag(R.id.viewer_adapter_item_data, item)
        photoView.setTag(R.id.viewer_adapter_item_key, item.id)
        photoView.setTag(R.id.viewer_adapter_item_holder, this)
        loadImage(item.url,photoView)
    }
}