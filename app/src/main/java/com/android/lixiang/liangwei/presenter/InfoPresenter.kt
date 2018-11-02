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

class InfoPresenter @Inject constructor() : BasePresenter<InfoView>() {
    @Inject
    lateinit var mInfoService: InfoService


    fun selectIllegal(id: String) {
        mInfoService.selectIllegal(id).execute(object : BaseObserver<SelectIllegalBean>() {
            override fun onNext(t: SelectIllegalBean) {
                super.onNext(t)
                mView.returnSelectIllegal(t)
            }
        }, lifecycleProvider)
    }
}
