package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface ApprovalInfomationService {
    fun selectExam(id: String): Observable<SelectExamBean>

}