package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.AddTagView
import com.android.lixiang.liangwei.presenter.view.AllInfoEditView
import com.android.lixiang.liangwei.presenter.view.AllInfoView
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.service.AddTagService
import com.android.lixiang.liangwei.service.AllInfoEditService
import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class AddTagPresenter @Inject constructor() : BasePresenter<AddTagView>() {
    @Inject
    lateinit var mAddTagService: AddTagService

    fun test() {
//        mView.test(mAllInfoService.test())
    }

    fun updateLabel(param: String, pagenum: String) {
        mAddTagService.updateLabel(param, pagenum).execute(object : BaseObserver<UpdateLabelBean>() {
            override fun onNext(t: UpdateLabelBean) {
                super.onNext(t)
                mView.returnUpdateLabel(t)
            }
        }, lifecycleProvider)
    }
}