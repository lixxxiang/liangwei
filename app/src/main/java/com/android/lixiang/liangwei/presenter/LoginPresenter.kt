package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.LoginView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {
    @Inject
    lateinit var mLoginService: LoginService


    fun login(phone: String, password: String){

        mLoginService.login(phone, password).execute(object : BaseObserver<LoginBean>() {
            override fun onNext(t: LoginBean) {
                super.onNext(t)
                mView.returnLogin(t)
            }
        }, lifecycleProvider)
    }
}
