package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectPictureBean

interface InfoCollectPictureView : BaseView {
    fun testfromPic(string:String)
    fun returnDatafromPic(s :InfoCollectPictureBean)

}