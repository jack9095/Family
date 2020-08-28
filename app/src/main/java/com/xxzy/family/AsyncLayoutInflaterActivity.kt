package com.xxzy.family

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater

class AsyncLayoutInflaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 异步加载 xml, 在 Activity.onCreate(xxx) 里面
        AsyncLayoutInflater(this).inflate(R.layout.activity_async_layout_inflater, null, object : AsyncLayoutInflater.OnInflateFinishedListener {
            override fun onInflateFinished(view: View, p1: Int, p2: ViewGroup?) {
                setContentView(view)
            }
        })
    }
}