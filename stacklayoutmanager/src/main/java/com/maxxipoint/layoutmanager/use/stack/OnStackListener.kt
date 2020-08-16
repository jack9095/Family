package com.maxxipoint.layoutmanager.use.stack

import androidx.recyclerview.widget.RecyclerView

interface OnStackListener {

    fun onSliding(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int)

    fun onSlided(viewHolder: RecyclerView.ViewHolder?, direction: Int)

    fun onItemClick(position: Int)
}