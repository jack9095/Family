package com.example.dragpicturegoback.utils

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_imageviewer_photoview.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 把 Bitmap 保存到文件中
 */
fun saveBitmapFile(bitmap: Bitmap, file: File) {
    var bos: BufferedOutputStream? = null
    try {
        bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bos.flush()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            bos?.close()
        } catch (ignore: Exception) {
        }
    }
}

// 判断是否在主线程中
fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

val mainHandler = Handler(Looper.getMainLooper())
// 在主线程中执行
fun runOnUIThread(block: () -> Unit) {
    if (isMainThread()) block() else mainHandler.post(block)
}

object X {
    var appContext: Context? = null
}

fun appContext() = X.appContext!!
fun toast(message: String) = runOnUIThread { Toast.makeText(appContext(), message, Toast.LENGTH_SHORT).show() }

// 状态栏高度
fun statusBarHeight(): Int {
    var height = 0
    val resourceId = appContext().resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        height = appContext().resources.getDimensionPixelSize(resourceId);
    }
    return height
}

fun loadImage(url: String,photoView: ImageView){
    Glide.with(photoView).load(url)
        .override(photoView.width, photoView.height)
        .placeholder(photoView.drawable)
        .into(photoView)
}