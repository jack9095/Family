package com.xxzy.family.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mouble.baselibrary.base.BaseViewModel
import com.xxzy.family.getData
import com.xxzy.family.model.MainBean

class DragPictureGoBackViewModel: BaseViewModel() {

    var liveData = MutableLiveData<MutableList<MainBean>>()

    fun setData() {
        liveData.value = getData()
    }
}