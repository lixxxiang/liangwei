package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.ResetPasswordBean
import io.reactivex.Observable

interface ResetPasswordService {
    fun test(): String
    fun getData(oldPassword:String,newPassWord:String): Observable<ResetPasswordBean>
}