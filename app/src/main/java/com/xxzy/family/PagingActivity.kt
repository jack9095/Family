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
