package com.xxzy.family

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dragpicturegoback.ImageViewerDialogFragment
import com.example.dragpicturegoback.Trans
import com.example.dragpicturegoback.utils.Config
import com.example.dragpicturegoback.utils.X
import com.example.dragpicturegoback.utils.statusBarHeight
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.xxzy.family.adapter.DragPictureGoBackAdapter
import com.xxzy.family.model.MainBean
import com.xxzy.family.viewmodel.DragPictureGoBackViewModel
import kotlinx.android.synthetic.main.activity_drag_picture_goback.*

class DragPictureGoBackActivity : BaseViewModelActivity<DragPictureGoBackViewModel>() {

    lateinit var adapter: DragPictureGoBackAdapter

    override fun isBindEventBusHere(): Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_drag_picture_goback

    override fun initData() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = DragPictureGoBackAdapter(getData(),this)
        recyclerView.adapter = adapter
    }

    override fun dataObserver() {

    }

    override fun providerVMClass(): Class<DragPictureGoBackViewModel> = DragPictureGoBackViewModel::class.java

    override fun initView() {
        X.appContext = this.applicationContext
        Config.TRANSITION_OFFSET_Y = statusBarHeight()
    }

    fun showViewer(clickedData: MainBean,index: Int) {
        Log.e("DragPictureActivity", "点击事件")
        val dialogFragment = ImageViewerDialogFragment.Factory().build()
        dialogFragment.setData(getData(),clickedData.id,index)
        dialogFragment.show(supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        Trans.mapping.clear()
    }
}
