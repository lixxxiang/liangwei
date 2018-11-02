package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.AllInfoEditView
import com.android.lixiang.liangwei.presenter.view.AllInfoView
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.service.AllInfoEditService
import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class AllInfoEditPresenter @Inject constructor() : BasePresenter<AllInfoEditView>() {
    @Inject
    lateinit var mAllInfoEditService: AllInfoEditService

    fun test() {
//        mView.test(mAllInfoService.test())
    }

    fun search(param: String, pagenum: String, pageSize: String) {
        mAllInfoEditService.search(param, pagenum, pageSize).execute(object : BaseObserver<SearchBean>() {
            override fun onNext(t: SearchBean) {
                super.onNext(t)
                mView.returnSearch(t)
            }
        }, lifecycleProvider)
    }

    fun listExamInfo(pagenum: String, pageSize: String) {
        mAllInfoEditService.listexaminfo(pagenum, pageSize).execute(object : BaseObserver<ListExamInfoBean>() {
            override fun onNext(t: ListExamInfoBean) {
                super.onNext(t)
                mView.returnListExamInfo(t)
            }
        }, lifecycleProvider)
    }

    fun deleteExamInfo(ids: String){
        mAllInfoEditService.deleteexaminfo(ids).execute(object : BaseObserver<DeleteExamInfoBean>() {
            override fun onNext(t: DeleteExamInfoBean) {
                super.onNext(t)
                mView.returnDeleteExamInfo(t)
            }
        }, lifecycleProvider)
    }

    fun deleteIllegalInfo(ids: String){
        mAllInfoEditService.deleteillegalinfo(ids).execute(object : BaseObserver<DeleteIllegalInfoBean>() {
            override fun onNext(t: DeleteIllegalInfoBean) {
                super.onNext(t)
                mView.returnDeleteIllegalInfo(t)
            }
        }, lifecycleProvider)
    }
}