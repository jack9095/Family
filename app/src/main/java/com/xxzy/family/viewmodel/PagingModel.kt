package com.xxzy.family.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mouble.baselibrary.base.BaseViewModel
import com.xxzy.family.model.Concert
import com.xxzy.family.model.ConcertFactory


class PagingModel:BaseViewModel() {

    // PagedList: 数据源获取的数据最终靠PagedList来承载。
    // 对于PagedList,我们可以这样来理解，它就是一页数据的集合。每请求一页，就是新的一个PagedList对象
    private var convertList: LiveData<PagedList<Concert>>? = null
    private var concertDataSource: DataSource<Int, Concert>? = null

    init {
        val concertFactory = ConcertFactory()
        concertDataSource = concertFactory.create()
        convertList = LivePagedListBuilder(concertFactory, 20).build()
    }

    fun invalidateDataSource() {
        concertDataSource?.invalidate()
    }

    fun getConvertList(): LiveData<PagedList<Concert>>? {
        return convertList
    }

    val livadata: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = "1"
            value = "2"
            value = "3"
            value = "4"
            value = "5"
        }
    }

}