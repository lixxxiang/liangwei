package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean

interface InfoCollectEntryView : BaseView {
    fun test(string: String)
    fun returnData(s: DetailBean)
    fun returnData2(s: DetailBean)
}