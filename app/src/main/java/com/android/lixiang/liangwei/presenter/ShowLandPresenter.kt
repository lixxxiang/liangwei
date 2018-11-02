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

class ShowLandPresenter @Inject constructor() : BasePresenter<ShowLandView>() {
    @Inject
    lateinit var showLandService: ShowLandService

    fun listLine() {
        showLandService.listLine().execute(object : BaseObserver<ListLineBean>() {
            override fun onNext(t: ListLineBean) {
                super.onNext(t)
                mView.returnListLand(t)
            }
        }, lifecycleProvider)
    }
}
