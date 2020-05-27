package com.example.dragpicturegoback

import android.util.LongSparseArray
import android.widget.ImageView

class Transformer {
    fun getView(key: Long): ImageView? = Trans.mapping[key]
}


object Trans {
    @JvmStatic
    val mapping = LongSparseArray<ImageView>()

}