package com.xxzy.family.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dragpicturegoback.Trans
import com.xxzy.family.DragPictureGoBackActivity
import com.xxzy.family.R
import com.xxzy.family.model.MainBean

class DragPictureGoBackAdapter: RecyclerView.Adapter<DragPictureGoBackAdapter.MyViewHolder> {

    private var lists: List<MainBean>? = null
    private var activity: DragPictureGoBackActivity? = null

    constructor(lists: MutableList<MainBean>, activity: DragPictureGoBackActivity){
        this.lists = lists
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, null))
    }

    override fun getItemCount(): Int = lists?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mainBean = lists!![position]
        Glide.with(holder.imageView).load(mainBean.url).into(holder.imageView)
        holder.textView.text = "$position"

        holder.itemView.tag = mainBean

        holder.imageView.setOnClickListener{
            Log.e("MainAdapter", "点击事件")
            activity?.showViewer(mainBean,position)
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        val mainBean = holder.itemView.tag as MainBean
        Trans.mapping.put(mainBean.id, holder.imageView)
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val mainBean = holder.itemView.tag as MainBean
        Trans.mapping.remove(mainBean.id)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textView = itemView.findViewById<TextView>(R.id.posTxt)
    }
}