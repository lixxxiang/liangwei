package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.AllInfoView
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class AllInfoPresenter @Inject constructor() : BasePresenter<AllInfoView>() {
    @Inject
    lateinit var mAllInfoService: AllInfoService

    fun test() {
//        mView.test(mAllInfoService.test())
    }

    fun search(param: String, pagenum: String, pageSize: String) {
        mAllInfoService.search(param, pagenum, pageSize).execute(object : BaseObserver<SearchBean>() {
            override fun onNext(t: SearchBean) {
                super.onNext(t)
                mView.returnSearch(t)
            }
        }, lifecycleProvider)
    }

    fun listExamInfo(pagenum: String, pageSize: String) {
        mAllInfoService.listexaminfo(pagenum, pageSize).execute(object : BaseObserver<ListExamInfoBean>() {
            override fun onNext(t: ListExamInfoBean) {
                super.onNext(t)
                mView.returnListExamInfo(t)
            }
        }, lifecycleProvider)
    }

}