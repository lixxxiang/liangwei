package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import javax.inject.Inject

class AddTagRepository @Inject constructor() {
    fun updateLabel(param: String, pagenum: String): Observable<UpdateLabelBean> {
        return RetrofitFactory("http://" +
                "59.110.161.48:8088" +
//                "59.110.162.194:5201" +
                "/").create(ApiService::class.java).updatelabel(param, pagenum)
    }
}