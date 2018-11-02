package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.RegBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean
import io.reactivex.Observable
import javax.inject.Inject

class SetUserNameRepository @Inject constructor() {
    fun reg(password: String, name: String, phone: String): Observable<RegBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).reg(password, name, phone)
    }
}