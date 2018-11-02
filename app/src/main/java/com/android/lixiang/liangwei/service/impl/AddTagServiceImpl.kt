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

class AddTagServiceImpl @Inject constructor() : AddTagService {
    override fun updateLabel(param: String, pagenum: String): Observable<UpdateLabelBean> {
        return addTagRepository.updateLabel(param, pagenum).flatMap(Function<UpdateLabelBean, ObservableSource<UpdateLabelBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var addTagRepository: AddTagRepository
}