package com.example.dragpicturegoback

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dragpicturegoback.adapter.ImageViewerAdapter
import com.example.dragpicturegoback.bean.ItemBean
import com.example.dragpicturegoback.utils.Config
import com.example.dragpicturegoback.utils.TransitionEndHelper
import com.example.dragpicturegoback.utils.TransitionStartHelper
import com.example.dragpicturegoback.utils.findViewWithKeyTag
import com.example.dragpicturegoback.viewmodel.ImageViewerActionViewModel
import com.example.dragpicturegoback.viewmodel.ImageViewerViewModel
import com.example.dragpicturegoback.viewmodel.ViewerActions
import kotlinx.android.synthetic.main.fragment_image_viewer_dialog.*
import kotlin.math.max

/**
 * 放大图片弹框
 * ViewPager2 的使用非常简单，甚至比ViewPager还要简单，只要熟悉RecyclerView的肯定会写ViewPager2,
 * ViewPager2 的适配器和 RecyclerView 一模一样
 */
open class ImageViewerDialogFragment : BaseDialogFragment() {
    private val imageViewerActionViewModel by lazy { ViewModelProvider(requireActivity()).get(ImageViewerActionViewModel::class.java) }
    private val viewModel by lazy { ViewModelProvider(this).get(ImageViewerViewModel::class.java) }
//    private val userCallback by lazy { requireViewerCallback() }

    // ViewPager2 的适配器和 RecyclerView 一模一样
    private val adapter by lazy { ImageViewerAdapter() }

    lateinit var transformer: Transformer
    var initKey: Long = 0 // 就是适配器数据类中的 id
    var position: Int = 0 // 点击图片进入大图的角标，方便 ViewPager2 判断是哪张图片

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (!Components.working) dismissAllowingStateLoss()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_viewer_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transformer = Transformer()
        // 设置 适配器中的 监听器
        adapter.setListener(adapterListener)
        (view_pager2.getChildAt(0) as? RecyclerView?)?.let {
            it.clipChildren = false
            it.itemAnimator = null
        }
        // 设置 viewPager2 的滑动方向，这里默认设置的是水平滚动
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 注册 viewPager2 的页面改变监听器
        view_pager2.registerOnPageChangeCallback(pagerCallback)
        // 设置屏幕外加载页面数量，也就是我们说的预加载数量，这里默认预加载一页
        view_pager2.offscreenPageLimit = 1
        view_pager2.adapter = adapter
        view_pager2.setCurrentItem(position,false)
        imageViewerActionViewModel.actionEvent.observe(viewLifecycleOwner, Observer(::handle))
    }

    // 给 adapter 设置数据
    fun setData(data: MutableList<out ItemBean>,initKey: Long,position: Int){
        adapter.setData(data)
        this.initKey = initKey
        this.position = position
    }

    private fun handle(action: Pair<String, Any?>?) {
        when (action?.first) {
            ViewerActions.SET_CURRENT_ITEM -> view_pager2.currentItem = max(action.second as Int, 0)
            ViewerActions.DISMISS -> onBackPressed()
        }
    }

    private val adapterListener by lazy {
        object : ImageViewerAdapterListener {
            override fun onInit(viewHolder: RecyclerView.ViewHolder) {
                TransitionStartHelper.start(this@ImageViewerDialogFragment, transformer.getView(initKey), viewHolder)
                background.changeToBackgroundColor(Config.VIEWER_BACKGROUND_COLOR)
//                userCallback.onInit(viewHolder)
            }

            override fun onDrag(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float) {
                background.updateBackgroundColor(fraction, Config.VIEWER_BACKGROUND_COLOR, Color.TRANSPARENT)
//                userCallback.onDrag(viewHolder, view, fraction)
            }

            override fun onRestore(viewHolder: RecyclerView.ViewHolder, view: View, fraction: Float) {
                background.changeToBackgroundColor(Config.VIEWER_BACKGROUND_COLOR)
//                userCallback.onRestore(viewHolder, view, fraction)
            }

            override fun onRelease(viewHolder: RecyclerView.ViewHolder, view: View) {
                val startView = (view.getTag(R.id.viewer_adapter_item_key) as? Long?)?.let { transformer.getView(it) }
                TransitionEndHelper.end(this@ImageViewerDialogFragment, startView, viewHolder)
                background.changeToBackgroundColor(Color.TRANSPARENT)
//                userCallback.onRelease(viewHolder, view)
            }
        }
    }

    private val pagerCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
//                userCallback.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                userCallback.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
//                userCallback.onPageSelected(position)
            }
        }
    }

    override fun showFailure(message: String?) {
        super.showFailure(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
        view_pager2.unregisterOnPageChangeCallback(pagerCallback)
    }

    override fun onBackPressed() {
        if (TransitionStartHelper.animating || TransitionEndHelper.animating) return
        Log.i("ImageViewerDFragment","onBackPressed ${view_pager2.currentItem}")

        val currentKey = adapter.getItemId(view_pager2.currentItem)
        view_pager2.findViewWithKeyTag(R.id.viewer_adapter_item_key, currentKey)?.let { endView ->
            val startView = transformer.getView(currentKey)
            background.changeToBackgroundColor(Color.TRANSPARENT)

            (endView.getTag(R.id.viewer_adapter_item_holder) as? RecyclerView.ViewHolder?)?.let {
                TransitionEndHelper.end(this, startView, it)
//                userCallback.onRelease(it, endView)
            }
        }
    }

    open class Factory {
        open fun build(): ImageViewerDialogFragment = ImageViewerDialogFragment()
    }
}
