package com.maxxipoint.layoutmanager.use.stack

import androidx.recyclerview.widget.RecyclerView

interface OnStackListener<T> {

    fun onSliding(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int)

    fun onSlided(viewHolder: RecyclerView.ViewHolder?, t: T?, direction: Int)

    fun onItemClick(position: Int)
}