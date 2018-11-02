package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.LoginBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import io.reactivex.Observable
import javax.inject.Inject

class AllInfoRepository @Inject constructor() {
    //    fun getDataFromRepository(phone: String, password: String): Observable<LoginBean> {
//        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).login(phone, password)
//    }
    fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return RetrofitFactory("http://" +
                "59.110.161.48:8088" +
//                "59.110.162.194:5201" +
                "/").create(ApiService::class.java).search(param, pagenum, pageSize)
    }

    fun listexaminfo(pagenum: String, pageSize: String): Observable<ListExamInfoBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).listexaminfo(pagenum, pageSize)
    }
}