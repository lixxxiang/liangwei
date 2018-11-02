package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.android.lixiang.liangwei.presenter.view.SetUserNameView
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.SetUserNameService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class SetUserNamePresenter @Inject constructor() : BasePresenter<SetUserNameView>() {
    @Inject
    lateinit var mSetUserNameService: SetUserNameService


    fun reg(password: String, name: String, phone: String) {
        mSetUserNameService.reg(password, name, phone).execute(object : BaseObserver<RegBean>() {
            override fun onNext(t: RegBean) {
                super.onNext(t)
                mView.returnReg(t)
            }
        }, lifecycleProvider)
    }
}
