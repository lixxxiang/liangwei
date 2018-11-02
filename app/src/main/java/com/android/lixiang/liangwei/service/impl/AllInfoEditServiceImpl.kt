package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.*
import com.android.lixiang.liangwei.service.*
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class AllInfoEditServiceImpl @Inject constructor() : AllInfoEditService {
    override fun deleteillegalinfo(ids: String): Observable<DeleteIllegalInfoBean> {
        return allInfoEditRepository.deleteillegalinfo(ids).flatMap(Function<DeleteIllegalInfoBean, ObservableSource<DeleteIllegalInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun deleteexaminfo(ids: String): Observable<DeleteExamInfoBean> {
        return allInfoEditRepository.deleteexaminfo(ids).flatMap(Function<DeleteExamInfoBean, ObservableSource<DeleteExamInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun listexaminfo(pagenum: String, pageSize: String): Observable<ListExamInfoBean> {
        return allInfoEditRepository.listexaminfo(pagenum, pageSize).flatMap(Function<ListExamInfoBean, ObservableSource<ListExamInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return allInfoEditRepository.search(param, pagenum, pageSize).flatMap(Function<SearchBean, ObservableSource<SearchBean>> { t ->
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
    lateinit var allInfoEditRepository: AllInfoEditRepository
}