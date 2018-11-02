package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoEditRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.InfoService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class InfoServiceImpl @Inject constructor() : InfoService {
    override fun selectIllegal(id: String): Observable<SelectIllegalBean> {
        return infoRepository.selectIllegal(id).flatMap(Function<SelectIllegalBean, ObservableSource<SelectIllegalBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var infoRepository: InfoRepository
}