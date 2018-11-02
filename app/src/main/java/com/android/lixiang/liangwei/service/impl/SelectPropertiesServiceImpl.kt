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

class SelectPropertiesServiceImpl @Inject constructor() : SelectPropertiesService {
    override fun updateStatus(param: String, pagenum: String): Observable<UpdateStatusBean> {
        return selectPropertiesRepository.updateStatus(param, pagenum).flatMap(Function<UpdateStatusBean, ObservableSource<UpdateStatusBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var selectPropertiesRepository: SelectPropertiesRepository
}