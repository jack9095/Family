package com.maxxipoint.layoutmanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.maxxipoint.layoutmanager.R
import com.maxxipoint.layoutmanager.bean.SlideBean
import com.maxxipoint.layoutmanager.use.stack.ItemTouchHelperCallback
import com.maxxipoint.layoutmanager.use.stack.OnStackListener
import com.maxxipoint.layoutmanager.use.stack.StackLayoutManager
import com.maxxipoint.layoutmanager.use.tool.TouchRecyclerView
import com.maxxipoint.layoutmanager.widget.BaseOverlayPageAdapter
//import com.maxxipoint.layoutmanager.widget.SimpleOverlayAdapter

class VpAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mList = mutableListOf<SlideBean?>()

    private val imgUrls = arrayOf(
        "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1383192171,3573826053&fm=26&gp=0.jpg"
        ,
        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1670905418,2793220050&fm=26&gp=0.jpg"
        ,
        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3391864021,2947070253&fm=26&gp=0.jpg"
        ,
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1705579171,700114950&fm=26&gp=0.jpg"
        ,
        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3577773561,2706257243&fm=26&gp=0.jpg"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_vp, parent, false)
            MyViewHolder(inflate)
        } else {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            TwoViewHolder(inflate)
        }
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val adapter = BaseOverlayPageAdapter(holder.itemView.context)
            adapter.setImgUrlsAndBindViewPager(holder.viewPager, imgUrls, 2)
            holder.viewPager.adapter = adapter
            holder.viewPager.currentItem = 5000
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 2) 0 else 1
    }

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewPager: ViewPager = itemView.findViewById(R.id.vp)
    }

    internal class TwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}