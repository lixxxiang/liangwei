package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.AllInfoRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.LoginRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class AllInfoServiceImpl @Inject constructor() : AllInfoService {
    override fun listexaminfo(pagenum: String, pageSize: String): Observable<ListExamInfoBean> {
        return allInfoRepository.listexaminfo(pagenum, pageSize).flatMap(Function<ListExamInfoBean, ObservableSource<ListExamInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return allInfoRepository.search(param, pagenum, pageSize).flatMap(Function<SearchBean, ObservableSource<SearchBean>> { t ->
            return@Function Observable.just(t)
        })
    }
//    override fun test() {
//    }

//    override fun login(phone: String, password: String): Observable<LoginBean> {
//        return loginRepository.getDataFromRepository(phone, password).flatMap(Function<LoginBean, ObservableSource<LoginBean>> { t ->
//            return@Function Observable.just(t)
//        })
//    }

    @Inject
    lateinit var allInfoRepository: AllInfoRepository
}