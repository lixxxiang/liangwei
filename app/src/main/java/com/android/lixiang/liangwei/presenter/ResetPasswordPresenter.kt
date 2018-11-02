package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.*
import com.android.lixiang.liangwei.service.*
import javax.inject.Inject

class ResetPasswordPresenter @Inject constructor(): BasePresenter<ResetPasswordView>(){
    @Inject
    lateinit var mResetPasswordService : ResetPasswordService

    fun test(){
        mView.testfromresetpassword(mResetPasswordService.test())
    }




    fun updatePassword(oldPassword:String,newPassword:String){
        mResetPasswordService.getData(oldPassword,newPassword).execute(object :BaseObserver<ResetPasswordBean>() {

            override fun onNext(t: ResetPasswordBean) {
                super.onNext(t)

                mView.returnDatafromresetpassword(t)
            }
        },lifecycleProvider)
    }
}