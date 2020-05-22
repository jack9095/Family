package com.xxzy.family.app

import android.app.Application
import android.content.Context
import com.mouble.baselibrary.http.HttpHelper
import com.mouble.baselibrary.http.UrlConfig
import com.mouble.baselibrary.util.LogUtil
import com.mouble.baselibrary.util.SharedPreferencesUtils


class WorkApplication : Application() {

    val aaa = "90"

    // 伴生对象
    companion object{
        private lateinit var INSTANCE: WorkApplication
        fun getInstance(): WorkApplication {
            return INSTANCE
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initLog()
        HttpHelper.getInstance()?.init(UrlConfig.BASE_URL) // 初始化网络请求
        SharedPreferencesUtils.getInstance("demo_fl", this.applicationContext)
    }

    private fun initLog() {
        val fLog = LogUtil.Builder(this)
            .isLog(true) //是否开启打印
            .isLogBorder(true) //是否开启边框
            .setLogType(LogUtil.TYPE.E) //设置默认打印级别
            .setTag("dx") //设置默认打印Tag
        LogUtil.init(fLog)
    }
}