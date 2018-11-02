package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectBean
import com.android.lixiang.liangwei.presenter.data.bean.LoginBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean

interface LoginView : BaseView {
    fun test(string:String)
    fun returnLogin(loginBean: LoginBean)
}