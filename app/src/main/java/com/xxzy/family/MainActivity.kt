package com.xxzy.family

import android.content.Intent
import androidx.lifecycle.Observer
import com.xxzy.family.viewmodel.MainViewModel
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.mouble.baselibrary.util.GsonUtils
import com.mouble.baselibrary.util.LogUtil
import com.xxzy.family.app.WorkApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseViewModelActivity<MainViewModel>() {

//    val TAG = MainActivity::class.simpleName
    val TAG = this.javaClass.simpleName

    override fun isBindEventBusHere(): Boolean = false
    override fun providerVMClass(): Class<MainViewModel>? = MainViewModel::class.java
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        val aaa = WorkApplication.getInstance().aaa
    }

    override fun initData() {
        viewModel.loadData()
//        round_rl.setRoundMode(3)
    }

    override fun dataObserver() {
        viewModel.dataLiveData.observe(this, Observer {
            LogUtil.e(TAG, "数据：" + GsonUtils.toJson(it))
//            startActivity(Intent(this@MainActivity,PagingActivity::class.java))
        })
    }

}
