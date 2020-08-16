package com.maxxipoint.layoutmanager.use.stack

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("ClickableViewAccessibility")
class StackLayoutManager() : RecyclerView.LayoutManager() {

    private var mRecyclerView: RecyclerView? = null
    private var mItemTouchHelper: ItemTouchHelper? = null
    private var mListener: OnStackListener? = null
    private var position = 0
    private var viewHolder: RecyclerView.ViewHolder? = null
    private val onTouchListener by lazy {
        View.OnTouchListener { v, event ->
            viewHolder = mRecyclerView?.getChildViewHolder(v)
            gestureDetector.onTouchEvent(event)
            true
        }
    }
    private val gestureDetector by lazy {
        GestureDetector(mRecyclerView?.context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                mListener?.onItemClick(position)
                return super.onSingleTapUp(e)
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                viewHolder?.let { mItemTouchHelper?.startSwipe(it) }
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        })
    }

    constructor(
        callback: ItemTouchHelperCallback<*>,
        rv: RecyclerView,
        ith: ItemTouchHelper
    ) : this() {
        this.mRecyclerView = rv
        this.mItemTouchHelper = ith

        callback.setOnStackListener(object : OnStackListener {
            override fun onSliding(
                viewHolder: RecyclerView.ViewHolder?,
                ratio: Float,
                direction: Int
            ) {
            }

            override fun onSlided(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                if (position >= itemCount - 1) position = 0 else position++
            }

            override fun onItemClick(position: Int) {}
        })
    }

    fun setOnStackListener(mListener: OnStackListener?) {
        this.mListener = mListener
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler?.let { detachAndScrapAttachedViews(it) }
        if (itemCount > ItemTouchHelperConfig.DEFAULT_SHOW_ITEM) {
            for (position in ItemTouchHelperConfig.DEFAULT_SHOW_ITEM downTo 0) {
                val view = recycler?.getViewForPosition(position)
                view?.let {
                    addView(it)
                    measureChildWithMargins(it, 0, 0)
                    val widthSpace = width - getDecoratedMeasuredWidth(it)
                    val heightSpace = height - getDecoratedMeasuredHeight(it)
                    layoutDecoratedWithMargins(
                        it, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(it),
                        heightSpace / 5 + getDecoratedMeasuredHeight(it)
                    )
                    when {
                        position == ItemTouchHelperConfig.DEFAULT_SHOW_ITEM -> {
                            it.scaleX = 1 - (position - 1) * ItemTouchHelperConfig.DEFAULT_SCALE
                            it.scaleY = 1 - (position - 1) * ItemTouchHelperConfig.DEFAULT_SCALE
                            it.translationX =
                                (position - 1) * it.measuredWidth / ItemTouchHelperConfig.DEFAULT_TRANSLATE.toFloat()
                        }
                        position > 0 -> {
                            it.scaleX = 1 - position * ItemTouchHelperConfig.DEFAULT_SCALE
                            it.scaleY = 1 - position * ItemTouchHelperConfig.DEFAULT_SCALE
                            it.translationX =
                                position * it.measuredWidth / ItemTouchHelperConfig.DEFAULT_TRANSLATE.toFloat()
                        }
                        else -> {
                            it.setOnTouchListener(onTouchListener)
                        }
                    }
                }
            }
        } else {
            for (position in itemCount - 1 downTo 0) {
                val view = recycler?.getViewForPosition(position)
                view?.let {
                    addView(it)
                    measureChildWithMargins(it, 0, 0)
                    val widthSpace = width - getDecoratedMeasuredWidth(it)
                    val heightSpace = height - getDecoratedMeasuredHeight(it)
                    layoutDecoratedWithMargins(
                        it, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(it),
                        heightSpace / 5 + getDecoratedMeasuredHeight(it)
                    )
                    if (position > 0) {
                        it.scaleX = 1 - position * ItemTouchHelperConfig.DEFAULT_SCALE
                        it.scaleY = 1 - position * ItemTouchHelperConfig.DEFAULT_SCALE
                        it.translationX =
                            position * it.measuredWidth / ItemTouchHelperConfig.DEFAULT_TRANSLATE.toFloat()
                    } else {
                        it.setOnTouchListener(onTouchListener)
                    }
                }
            }
        }
    }
}