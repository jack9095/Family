package com.xxzy.family

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import com.example.dragpicturegoback.ImageViewerDialogFragment
import com.xxzy.family.viewmodel.MainViewModel
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.mouble.baselibrary.util.GsonUtils
import com.mouble.baselibrary.util.LogUtil
import com.xxzy.family.app.WorkApplication
import com.xxzy.family.data.getData
import com.xxzy.family.dialog.InputDialogFragment
import com.xxzy.family.dialog.SoftKeyBoardListener
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
//        LogUtil.e("aaaaaaaaaaa")
        val obtainMessage = handler.obtainMessage()
        obtainMessage.obj = "bbbbbbbbbb"
        handler.sendMessage(obtainMessage)
//        LogUtil.e("cccccccc")
        addView.setOnClickListener(this)
        immediately.setOnClickListener(this)

        SoftKeyBoardListener.setListener(this@MainActivity, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener{
            override fun keyBoardShow(height: Int) {
                val lp = bottom_rl.layoutParams as? RelativeLayout.LayoutParams
                lp?.setMargins(0,0,0,height)
                bottom_rl.layoutParams = lp
                bottom_rl.visibility = View.VISIBLE
                LogUtil.e("键盘显示 高度$height")
            }

            override fun keyBoardHide(height: Int) {
                bottom_rl.visibility = View.GONE
                LogUtil.e("键盘隐藏 高度$height")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val obtainMessage = handler.obtainMessage()
        obtainMessage.obj = "ddddddd"
        handler.sendMessage(obtainMessage)


        handler.post{
//            LogUtil.e("eeeeeee")
        }
//        LogUtil.e("ffffffffff")
    }

    override fun initData() {
        viewModel.loadData()
        round_rl.setRoundMode(3)
//        round_rl.setRadius(4f)
    }

    override fun dataObserver() {
        viewModel.dataLiveData.observe(this, Observer {
//            LogUtil.e(TAG, "数据：" + GsonUtils.toJson(it))
//            startActivity(Intent(this@MainActivity,DragPictureGoBackActivity::class.java))
        })
    }

    override fun onClick(v: View?) {
        when(v){
            addView -> {
                startActivity(Intent(this,DragPictureGoBackActivity::class.java))
            }
            immediately -> {
                val dialogFragment = InputDialogFragment.Factory().build()
                dialogFragment.show(supportFragmentManager)
//                startActivity(Intent(this,PagingActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        LogUtil.e("MainActivity 返回键")
    }

}
