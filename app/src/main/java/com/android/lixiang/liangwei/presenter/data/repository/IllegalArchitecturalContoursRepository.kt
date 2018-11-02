package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean
import io.reactivex.Observable
import javax.inject.Inject

class IllegalArchitecturalContoursRepository @Inject constructor() {
    fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).search(param, pagenum, pageSize)
    }
}