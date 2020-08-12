package com.xxzy.family

import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import com.mouble.baselibrary.base.BaseViewModelActivity
import com.mouble.baselibrary.util.LogUtil
import com.xxzy.family.time.*
import com.xxzy.family.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_time.*
import java.security.AccessController

class TimeActivity : BaseViewModelActivity<MainViewModel>(), View.OnClickListener {

    /**
     * 倒计时的hook
     */
//    private var mFlashHook: AbsCountdownHook? = null

    /**
     * 倒计时(秒)
     */
//    private val countdown = 320

    override fun isBindEventBusHere(): Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_time

    override fun initData() {

    }

    override fun dataObserver() {

    }

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {

        // 顶部倒计时
//        mFlashHook = object : AbsCountdownHook(countdown.toLong()) {
//            override fun getCountdownView(): TextViewTimer {
//                return tvFlashTime
//            }
//
//            override fun isViewActive(): Boolean {
//                return AccessController.getContext() != null
//            }
//
//            override fun doTimeOver() {
//                Toast.makeText(this@TimeActivity, "时间到了", Toast.LENGTH_SHORT).show()
//            }
//        }

        val timeViewListener: TimeViewListener = CountDownDefault()
        tvFlashTime?.setListener(timeViewListener)
        countDownTimer("86400000")
//        CountdownManager.getInstance().start("haha", mFlashHook)
    }

    var countDownTimer: CountDownTimer? = null

    /**
     * 定时器方法封装
     * param: countDown 毫秒
     * param: tempTime 是在滚动列表中复用记录当前时间的，防止倒计时错乱，不复用默认为0
     */
    private fun countDownTimer(
        countDown: String, tempTime: Long = 0,
        countDownMap: SparseArray<CountDownTimer>? = null
    ) {
        if (!TextUtils.isEmpty(countDown) && "null" != countDown) {

            var countDownNumber = countDown.toLong() * 8

//            val timeStamp = System.currentTimeMillis() - tempTime
//            val time = countDownNumber - timeStamp

            if (countDownNumber < 1000) {
                countDownNumber = 1000
            }

            countDownTimer?.cancel()
            countDownTimer = object : CountDownTimer(countDownNumber, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val second = millisUntilFinished / 1000
                    val hours = second / 3600
                    val minutes = second / 60
                    LogUtil.e(second)
                    val timeArrWithDay = ToolDate.getDayHourMinSecond(second)
                    Log.e("TimeActivity","数组大小${timeArrWithDay.size}")
                    tvFlashTime?.refreshTime(timeArrWithDay)
                }

                override fun onFinish() {

                }
            }.start()
//                countDownMap.put(itemView.hashCode(), countDownTimer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    override fun onClick(v: View?) {

    }
}