package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {
    @Inject
    lateinit var mRegisterService: RegisterService


    fun send(phone: String) {

        mRegisterService.send(phone).execute(object : BaseObserver<SendBean>() {
            override fun onNext(t: SendBean) {
                super.onNext(t)
                mView.returnSend(t)
            }
        }, lifecycleProvider)
    }

    fun verify(phone: String, code: String) {

        mRegisterService.verify(phone, code).execute(object : BaseObserver<VerifyBean>() {
            override fun onNext(t: VerifyBean) {
                super.onNext(t)
                mView.returnVerify(t)
            }
        }, lifecycleProvider)
    }
}
