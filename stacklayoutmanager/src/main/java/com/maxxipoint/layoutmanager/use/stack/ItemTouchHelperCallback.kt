package com.maxxipoint.layoutmanager.use.stack

import android.graphics.Canvas
import androidx.recyclerview.widget.*
import kotlin.math.abs

class ItemTouchHelperCallback<T>() : ItemTouchHelper.Callback() {

    private var adapter: RecyclerView.Adapter<*>? = null
    private var dataList: MutableList<T?>? = null
    private var mListener: OnStackListener<T>? = null

    constructor(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        dataList: MutableList<T?>
    ) : this() {
        this.adapter = adapter
        this.dataList = dataList
    }

    fun setOnStackListener(mListener: OnStackListener<T>?) {
        this.mListener = mListener
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        var slideFlags = 0
        if (recyclerView.layoutManager is StackLayoutManager<*>) {
            slideFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, slideFlags)
    }

    override fun onMove(
        rv: RecyclerView,
        vh: RecyclerView.ViewHolder,
        vh1: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.itemView.setOnTouchListener(null)
        val layoutPosition = viewHolder.layoutPosition
        val remove = dataList?.removeAt(layoutPosition)
        dataList?.add(remove)
        adapter?.notifyDataSetChanged()
        mListener?.onSlided(
            viewHolder,remove,
            if (direction == ItemTouchHelper.LEFT) ItemTouchHelperConfig.SLIDED_LEFT else ItemTouchHelperConfig.SLIDED_RIGHT
        )
    }

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.rotation = 0f
    }

    private fun getThreshold(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Float {
        return recyclerView.width * getSwipeThreshold(viewHolder)
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var ratio = dX / getThreshold(recyclerView, viewHolder)
            if (ratio > 1) ratio = 1f
            else if (ratio < -1) ratio = -1f
            itemView.rotation = ratio * ItemTouchHelperConfig.DEFAULT_ROTATE_DEGREE
            val childCount = recyclerView.childCount
            if (childCount > ItemTouchHelperConfig.DEFAULT_SHOW_ITEM) {
                for (position in 1 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * ItemTouchHelperConfig.DEFAULT_SCALE + abs(ratio) * ItemTouchHelperConfig.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * ItemTouchHelperConfig.DEFAULT_SCALE + abs(ratio) * ItemTouchHelperConfig.DEFAULT_SCALE
                    view.translationX =
                        (index - abs(ratio)) * itemView.measuredWidth / ItemTouchHelperConfig.DEFAULT_TRANSLATE
                }
            } else {
                for (position in 0 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * ItemTouchHelperConfig.DEFAULT_SCALE + abs(ratio) * ItemTouchHelperConfig.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * ItemTouchHelperConfig.DEFAULT_SCALE + abs(ratio) * ItemTouchHelperConfig.DEFAULT_SCALE
                    view.translationX =
                        (index - abs(ratio)) * itemView.measuredWidth / ItemTouchHelperConfig.DEFAULT_TRANSLATE
                }
            }
            mListener?.run {
                if (ratio != 0f) {
                    onSliding(
                        viewHolder,
                        ratio,
                        if (ratio < 0) ItemTouchHelperConfig.SLIDING_LEFT else ItemTouchHelperConfig.SLIDING_RIGHT
                    )
                } else {
                    onSliding(viewHolder, ratio, ItemTouchHelperConfig.SLIDING_NONE)
                }
            }
        }
    }
}