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

class ResultInfoServiceImpl @Inject constructor() : ResultInfoService {

    override fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return resultInfoRepository.search(param, pagenum, pageSize).flatMap(Function<SearchBean, ObservableSource<SearchBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var resultInfoRepository: ResultInfoRepository
}