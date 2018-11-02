package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.LoginBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import io.reactivex.Observable
import javax.inject.Inject

class LoginRepository @Inject constructor() {
    fun getDataFromRepository(phone: String, password: String): Observable<LoginBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).login(phone, password)
    }
}