package com.example.dragpicturegoback.utils

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.dragpicturegoback.bean.ItemBean
import com.example.dragpicturegoback.viewholders.PhotoViewHolder
import com.example.dragpicturegoback.viewholders.SubsamplingViewHolder
import kotlinx.android.synthetic.main.item_imageviewer_photoview.*
import kotlinx.android.synthetic.main.item_imageviewer_subsampling.*

/**
 * https://www.jianshu.com/p/c02c079f127d
 *
 * Transition 过渡动画
 */
object TransitionStartHelper {
    var animating = false

    fun start(owner: LifecycleOwner, startView: View?, holder: RecyclerView.ViewHolder) {
        beforeTransition(startView, holder)
        val doTransition = {
            TransitionManager.beginDelayedTransition(holder.itemView as ViewGroup, transitionSet().also {
                it.addListener(object : TransitionListenerAdapter() {
                    override fun onTransitionStart(transition: Transition) {
                        animating = true
                    }

                    override fun onTransitionEnd(transition: Transition) {
                        animating = false
                        afterTransition(holder)
                    }
                })
            })
            transition(holder)
        }
        holder.itemView.postDelayed(doTransition, 50)
        owner.onDestroy {
            holder.itemView.removeCallbacks(doTransition)
            TransitionManager.endTransitions(holder.itemView as ViewGroup)
        }
    }

    private fun beforeTransition(startView: View?, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is PhotoViewHolder -> {
                holder.photoView.scaleType = (startView as? ImageView?)?.scaleType
                        ?: ImageView.ScaleType.FIT_CENTER
                holder.photoView.layoutParams = holder.photoView.layoutParams.apply {
                    width = startView?.width ?: width
                    height = startView?.height ?: height
                    val location = IntArray(2)
                    getLocationOnScreen(startView, location)
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = location[0]
                        topMargin = location[1] - Config.TRANSITION_OFFSET_Y
                    }
                }
            }
            is SubsamplingViewHolder -> {
                holder.subsamplingView.layoutParams = holder.subsamplingView.layoutParams.apply {
                    width = startView?.width ?: width
                    height = startView?.height ?: height
                    val location = IntArray(2)
                    getLocationOnScreen(startView, location)
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = location[0]
                        topMargin = location[1] - Config.TRANSITION_OFFSET_Y
                    }
                }
            }
        }
    }

    private fun transition(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is PhotoViewHolder -> {
                holder.photoView.scaleType = ImageView.ScaleType.FIT_CENTER
                holder.photoView.layoutParams = holder.photoView.layoutParams.apply {
                    width = MATCH_PARENT
                    height = MATCH_PARENT
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = 0
                        topMargin = 0
                    }
                }
            }
            is SubsamplingViewHolder -> {
                holder.subsamplingView.layoutParams = holder.subsamplingView.layoutParams.apply {
                    width = MATCH_PARENT
                    height = MATCH_PARENT
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = 0
                        topMargin = 0
                    }
                }
            }
        }
    }

    private fun transitionSet(): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            // https://github.com/davemorrissey/subsampling-scale-image-view/issues/313
            duration = Config.DURATION_TRANSITION
            interpolator = DecelerateInterpolator()
        }
    }

    private fun getLocationOnScreen(startView: View?, location: IntArray) {
        startView?.getLocationOnScreen(location)
        if (startView?.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
            location[0] = startView.context.resources.displayMetrics.widthPixels - location[0] - startView.width
        }
    }

    private fun afterTransition(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is PhotoViewHolder -> {
                val itemData = holder.photoView.getTag(Config.ADAPTER_PHOTO_VIEW_DATA) as ItemBean
                loadImage(itemData.url,holder.photoView)
            }
        }
    }
}