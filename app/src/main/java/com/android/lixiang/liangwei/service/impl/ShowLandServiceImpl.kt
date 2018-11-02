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

class ShowLandServiceImpl @Inject constructor() : ShowLandService {
    override fun listLine(): Observable<ListLineBean> {
        return showLandRepository.listline().flatMap(Function<ListLineBean, ObservableSource<ListLineBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var showLandRepository: ShowLandRepository
}