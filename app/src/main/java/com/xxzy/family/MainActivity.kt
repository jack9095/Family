package com.xxzy.family

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.lifecycle.Observer
import com.xxzy.family.viewmodel.MainViewModel
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.mouble.baselibrary.util.GsonUtils
import com.mouble.baselibrary.util.LogUtil
import com.xxzy.family.app.WorkApplication
import com.xxzy.family.widget.PieShapeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseViewModelActivity<MainViewModel>(), View.OnClickListener {

//    val TAG = MainActivity::class.simpleName
    val TAG = this.javaClass.simpleName
    var handler = Handler(object : Handler.Callback{
        override fun handleMessage(msg: Message): Boolean {
            LogUtil.e(msg.obj)

            return false
        }
    })

    override fun isBindEventBusHere(): Boolean = false
    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        val aaa = WorkApplication.getInstance().aaa
        LogUtil.e("aaaaaaaaaaa")
        val obtainMessage = handler.obtainMessage()
        obtainMessage.obj = "bbbbbbbbbb"
        handler.sendMessage(obtainMessage)
        LogUtil.e("cccccccc")
        addView.setOnClickListener(this)
        immediately.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        val obtainMessage = handler.obtainMessage()
        obtainMessage.obj = "ddddddd"
        handler.sendMessage(obtainMessage)


        handler.post{
            LogUtil.e("eeeeeee")
        }
        LogUtil.e("ffffffffff")
    }

    override fun initData() {
        viewModel.loadData()
        round_rl.setRoundMode(3)
    }

    override fun dataObserver() {
        viewModel.dataLiveData.observe(this, Observer {
            LogUtil.e(TAG, "数据：" + GsonUtils.toJson(it))
//            startActivity(Intent(this@MainActivity,DragPictureGoBackActivity::class.java))
        })
    }

    override fun onClick(v: View?) {
        when(v){
            addView -> {
                startActivity(Intent(this,DragPictureGoBackActivity::class.java))
            }
            immediately -> {
                startActivity(Intent(this,PagingActivity::class.java))
            }
        }
    }

}
