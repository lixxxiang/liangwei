package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.AllInfoView
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.ResultInfoEditView
import com.android.lixiang.liangwei.presenter.view.ResultInfoView
import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.ResultInfoEditService
import com.android.lixiang.liangwei.service.ResultInfoService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class ResultInfoEditPresenter @Inject constructor() : BasePresenter<ResultInfoEditView>() {
    @Inject
    lateinit var mResultInfoEditService: ResultInfoEditService

    fun test() {
    }

    fun search(param: String, pagenum: String, pageSize: String) {
        mResultInfoEditService.search(param, pagenum, pageSize).execute(object : BaseObserver<SearchBean>() {
            override fun onNext(t: SearchBean) {
                super.onNext(t)
                mView.returnSearch(t)
            }
        }, lifecycleProvider)
    }
}