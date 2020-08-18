package com.maxxipoint.layoutmanager.use

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxxipoint.layoutmanager.R
//import com.maxxipoint.layoutmanager.adapter.StackAdapter
import com.maxxipoint.layoutmanager.adapter.VpAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        A("33333")
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = StackAdapter()
        recyclerView.adapter = VpAdapter()
    }
}