package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.presenter.data.repository.SetUserNameRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.SetUserNameService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class SetUserNameServiceImpl @Inject constructor() : SetUserNameService {
    override fun reg(password: String, name: String, phone: String): Observable<RegBean> {
        return setUserNameRepository.reg(password, name, phone).flatMap(Function<RegBean, ObservableSource<RegBean>> { t ->
            return@Function Observable.just(t)
        })
    }


    @Inject
    lateinit var setUserNameRepository: SetUserNameRepository

}