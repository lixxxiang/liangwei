package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.IllegalInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SelectIllegalBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean
import io.reactivex.Observable
import javax.inject.Inject

class InfoRepository @Inject constructor() {
    fun selectIllegal(id: String): Observable<SelectIllegalBean> {
        return RetrofitFactory("http://" +
//                "59.110.162.194:5201" +
                "59.110.161.48:8088" +
                "/").create(ApiService::class.java).selectillegal(id)
    }
    //10.10.90.32
}