package com.xxzy.family.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxzy.family.R
import com.xxzy.family.model.Concert
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_paging.*

/**
 * 实现了 LayoutContainer 接口，就可以使用 kotlin 中自动实现 findViewById 的功能，导入布局就好了
 */
class TestPagingViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
//    var mTitleTextView: TextView = itemView.findViewById(R.id.title)
//    var mAuthorTextView: TextView = itemView.findViewById(R.id.author)
//    var mContentTextView: TextView = itemView.findViewById(R.id.content)

    fun bind(item: Concert?) {
        item?.let {
            title_tv.text = it.title
            author.text = it.author
            content.text = it.content
        }
    }
}