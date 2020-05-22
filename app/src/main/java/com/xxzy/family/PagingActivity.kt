package com.xxzy.family

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.xxzy.family.adapter.RecyclerAdapter
import com.xxzy.family.viewmodel.PagingModel
import kotlinx.android.synthetic.main.activity_paging.*

/**
 * https://www.jianshu.com/p/bbb96fc62bcd
 *
 * https://blog.csdn.net/LitterLy/article/details/105398543
 */
class PagingActivity : BaseViewModelActivity<PagingModel>() {

   lateinit var adapter: RecyclerAdapter

    override fun isBindEventBusHere(): Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_paging

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        adapter = RecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {

        // 只需要使用adapter.submitList(List)方法,就会自动更新数据，不用去自动调用 notifyDataSetChanged
        // 但是需要注意一个问题，这个adapter.submitList(List)方法中需要提供一个列表，这个List必须是一个新的
        // 列表，也就是说，如果你使用的是一个已经加载了的列表，那么将不会被加载
        viewModel. getConvertList()?.observe(this, Observer(adapter::submitList))
//        viewModel. getConvertList()?.observe(this, Observer {
//
//            adapter.submitList(it)
//        })
        recyclerView.adapter = adapter
    }

    override fun dataObserver() {

    }

    override fun providerVMClass(): Class<PagingModel>? = PagingModel::class.java

    override fun initView() {

    }
}
