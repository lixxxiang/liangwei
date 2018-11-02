package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectBean

interface InfoCollectView : BaseView {
    fun test(string:String)
    fun returnData(s :DetailBean)
    fun returnData2(s :DetailBean)
    fun returnData3(s : InfoCollectBean)
}