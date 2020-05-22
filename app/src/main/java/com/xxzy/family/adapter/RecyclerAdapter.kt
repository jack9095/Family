package com.xxzy.family.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.xxzy.family.R
import com.xxzy.family.model.Concert

/**
 * 使用 Paging 实现的 适配器
 * PagedListAdapter:这个Adapter就是一个 RecyclerView 的 Adapter。
 * 不过我们在使用paging实现RecyclerView的分页加载效果，不能直接继承RecyclerView的Adapter，而是需要继承 PagedListAdapter
 */
class RecyclerAdapter: PagedListAdapter<Concert, RecyclerAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val concert = getItem(position)
        if (concert != null) {
            holder.mTitleTextView.text = concert.title
            holder.mAuthorTextView.text = concert.author
            holder.mContentTextView.text = concert.content
        }
    }

    inner class RecyclerViewHolder(itemView: View) : ViewHolder(itemView) {
        var mTitleTextView: TextView = itemView.findViewById(R.id.title)
        var mAuthorTextView: TextView = itemView.findViewById(R.id.author)
        var mContentTextView: TextView = itemView.findViewById(R.id.content)
    }
}

private val DIFF_CALLBACK: DiffUtil.ItemCallback<Concert> =
    object : DiffUtil.ItemCallback<Concert>() {
        override fun areItemsTheSame(oldConcert: Concert, newConcert: Concert): Boolean {
            return oldConcert.title == newConcert.title
        }

        override fun areContentsTheSame(
            oldConcert: Concert,
            newConcert: Concert
        ): Boolean {
            return oldConcert == newConcert
        }
    }