package com.maxxipoint.layoutmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maxxipoint.layoutmanager.R
import com.maxxipoint.layoutmanager.bean.SlideBean
import com.maxxipoint.layoutmanager.use.stack.ItemTouchHelperCallback
import com.maxxipoint.layoutmanager.use.stack.OnStackListener
import com.maxxipoint.layoutmanager.use.stack.StackLayoutManager
import com.maxxipoint.layoutmanager.use.tool.TouchRecyclerView

class StackAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnStackListener {

    private val mList = mutableListOf<SlideBean?>()

    init {
        val bgs = intArrayOf(
            R.mipmap.img_slide_1,
            R.mipmap.img_slide_2,
            R.mipmap.img_slide_3,
            R.mipmap.img_slide_4,
            R.mipmap.img_slide_5,
            R.mipmap.img_slide_6
        )

        for (i in 0..5) {
            mList.add(SlideBean(bgs[i]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_stack, parent, false)
            MyViewHolder(inflate)
        } else {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            TwoViewHolder(inflate)
        }
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.recyclerView.adapter = SlideChilAdapter(mList)
            holder.recyclerView.adapter?.let {
                val callback = ItemTouchHelperCallback<SlideBean?>(it, mList)
                val mItemTouchHelper = ItemTouchHelper(callback)
                val mSlideLayoutManager = StackLayoutManager(callback, holder.recyclerView, mItemTouchHelper)
                mSlideLayoutManager.setOnStackListener(this)
                mItemTouchHelper.attachToRecyclerView(holder.recyclerView)
                holder.recyclerView.layoutManager = mSlideLayoutManager
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 2) 0 else 1
    }

    override fun onSliding(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int) {}

    override fun onSlided(viewHolder: RecyclerView.ViewHolder?, direction: Int) {}

    override fun onItemClick(position: Int) {

    }

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerView: TouchRecyclerView = itemView.findViewById(R.id.recycler_view)

        init {
            recyclerView.itemAnimator = DefaultItemAnimator()
        }
    }

    internal class TwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}