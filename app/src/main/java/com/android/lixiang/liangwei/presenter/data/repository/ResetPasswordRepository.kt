package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.ResetPasswordBean
import io.reactivex.Observable
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor() {
    fun getDataFromRepository(s1: String, s2: String): Observable<ResetPasswordBean> {
        return RetrofitFactory("http://202.111.178.10:34567/").create(ApiService::class.java).updatePassword(s1, s2)
    }

//    fun getDataFromRepository3(s1: String, s2: String, s3: String, s4: String, s5: String, s6: String, s7: String, s8: String, s9: String, s10: String): Observable<InfoCollectBean> {
//        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).infoCollect(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
//    }
}