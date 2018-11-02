package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.*

interface IllegalArchitecturalContoursView : BaseView {
    fun test(string:String)
    fun returnSearch(searchBean: SearchBean)
}