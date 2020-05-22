package com.xxzy.family.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xxzy.family.R
import com.xxzy.family.model.Concert

/**
 * 使用 Paging 实现的 适配器
 * PagedListAdapter:这个Adapter就是一个 RecyclerView 的 Adapter。
 * 不过我们在使用paging实现RecyclerView的分页加载效果，不能直接继承RecyclerView的Adapter，而是需要继承 PagedListAdapter
 */
class RecyclerAdapter: PagedListAdapter<Concert, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging, parent, false)
        return TestPagingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val concert = getItem(position)
        if (concert != null && holder is TestPagingViewHolder) {
            holder.bind(concert)
//            holder.mTitleTextView.text = concert.title
//            holder.mAuthorTextView.text = concert.author
//            holder.mContentTextView.text = concert.content
        }
    }
}

// 最主要的功能就是处理adapter的更新，其功能就是比较两个数据集，
// 用newList和oldList进行比较，得出最小的变化量。也就是说我们不需要再无脑的使用 notifyDataSetChanged()
private val DIFF_CALLBACK: DiffUtil.ItemCallback<Concert> =
    object : DiffUtil.ItemCallback<Concert>() {
        // areItemsTheSame 提供了两个对象，需你提供这个两个对象是否是同一个对象。在User对象中有一个userId,
        // 其代表了唯一性，所以这里我就使用了oldItem?.userId == newItem?.userId。这个可根据实际情况自行判断
        override fun areItemsTheSame(oldConcert: Concert, newConcert: Concert): Boolean {
            return oldConcert.title == newConcert.title
        }

        // areContentsTheSame 也提供了两个对象，然后需要你提供这个两个对象的内容是否一致，如果不一致，那么
        // 它就将对列表进行重绘和动画加载，反之，表示你已经显示了这个对象的内容并且没有任何的变化，那么将不做任何的操作
        override fun areContentsTheSame(oldConcert: Concert, newConcert: Concert): Boolean {
            return oldConcert == newConcert
        }
    }