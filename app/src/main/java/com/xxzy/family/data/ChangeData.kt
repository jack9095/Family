package com.xxzy.family.data

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import com.xxzy.family.model.MainBean
import java.util.*

val mainHandler = Handler(Looper.getMainLooper())
var array = arrayOf(
        "https://b-ssl.duitang.com/uploads/item/201206/29/20120629140234_QWAsX.thumb.700_0.gif",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168118542&di=437ba348dfe4bd91afa5e5761f318cee&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201410%2F17%2F20141017094107_VdNJu.gif",
        "https://f12.baidu.com/it/u=3294379970,949120920&fm=72",
        "https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/145939/pexels-photo-145939.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
        "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg")

fun getData(): MutableList<MainBean> {
    val lists: MutableList<MainBean> = ArrayList()
    var mainBean: MainBean
    var count = 0L
    for (str in listOf(*array)) {
        mainBean = MainBean()
        mainBean.url = str
        mainBean.type = 0
        mainBean.id = count++
        lists.add(mainBean)
    }
    return lists
}

fun queryBefore(id: Long, callback: (List<MainBean>) -> Unit) {
    // 返回第一个符合条件的元素的下标，没有就返回-1 。
    val idx = getData().indexOfFirst { it.id == id }
    mainHandler.postDelayed({
        if (idx < 0) {
            callback(emptyList())
            return@postDelayed
        }
//        val result = getData().subList(max(idx - 5, 0), idx)
        val result = getData().subList(0, idx)
        callback(result)
    }, 100)
}

fun queryAfter(id: Long, callback: (List<MainBean>) -> Unit) {
    val idx = getData().indexOfFirst { it.id == id }
    mainHandler.postDelayed({
        if (idx < 0) {
            callback(emptyList())
            return@postDelayed
        }

//        val result = getData().subList(idx + 1, min(idx + 5, getData().size - 1))
        val result = getData()
            .subList(idx + 1, getData().size)
        callback(result)
    }, 100)
}


//fun setData(): MutableList<PieData>{
//
//    val lists = mutableListOf<PieData>()
//    lists.add(PieData(Color.parseColor("#000000"),0,90))
//    lists.add(PieData(Color.parseColor("#ff0000"),90,200))
//    lists.add(PieData(Color.parseColor("#00ff00"),200,290))
//    return lists
//}




