package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean

interface RegisterView : BaseView {
    fun test(string:String)
    fun returnSend(sendBean: SendBean)
    fun returnVerify(verifyBean: VerifyBean)

}