package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface RegisterService {
    fun send(phone: String): Observable<SendBean>
    fun verify(phone: String, code: String): Observable<VerifyBean>
}