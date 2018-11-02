package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class RegisterServiceImpl @Inject constructor() : RegisterService {
    override fun verify(phone: String, code: String): Observable<VerifyBean> {
        return registerRepository.verify(phone, code).flatMap(Function<VerifyBean, ObservableSource<VerifyBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var registerRepository: RegisterRepository

    override fun send(phone: String): Observable<SendBean> {
        return registerRepository.send(phone).flatMap(Function<SendBean, ObservableSource<SendBean>> { t ->
            return@Function Observable.just(t)
        })
    }
}