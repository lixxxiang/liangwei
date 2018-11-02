package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.*
import com.android.lixiang.liangwei.service.*
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class SelectPropertiesPresenter @Inject constructor() : BasePresenter<SelectPropertiesView>() {
    @Inject
    lateinit var mSelectPropertiesService: SelectPropertiesService

    fun test() {
//        mView.test(mAllInfoService.test())
    }

    fun updateLabel(param: String, pagenum: String) {
        mSelectPropertiesService.updateStatus(param, pagenum).execute(object : BaseObserver<UpdateStatusBean>() {
            override fun onNext(t: UpdateStatusBean) {
                super.onNext(t)
                mView.returnUpdateStatus(t)
            }
        }, lifecycleProvider)
    }
}