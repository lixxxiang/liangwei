package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.IllegalArchitecturalContoursRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoEditRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.IllegalArchitecturalContoursService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class IllegalArchitecturalContoursServiceImpl @Inject constructor() : IllegalArchitecturalContoursService {
    override fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean> {
        return illegalArchitecturalContoursRepository.search(param, pagenum, pageSize).flatMap(Function<SearchBean, ObservableSource<SearchBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var illegalArchitecturalContoursRepository: IllegalArchitecturalContoursRepository
}