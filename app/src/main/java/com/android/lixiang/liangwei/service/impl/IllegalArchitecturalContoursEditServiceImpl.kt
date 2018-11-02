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

class IllegalArchitecturalContoursEditServiceImpl @Inject constructor() : IllegalArchitecturalContoursEditService {
    override fun test() {
    }

//    override fun login(phone: String, password: String): Observable<LoginBean> {
//        return loginRepository.getDataFromRepository(phone, password).flatMap(Function<LoginBean, ObservableSource<LoginBean>> { t ->
//            return@Function Observable.just(t)
//        })
//    }

    @Inject
    lateinit var illegalArchitecturalContoursEditRepository: IllegalArchitecturalContoursEditRepository
}