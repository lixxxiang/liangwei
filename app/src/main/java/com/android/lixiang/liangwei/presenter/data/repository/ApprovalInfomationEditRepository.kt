package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

class ApprovalInfomationEditRepository @Inject constructor() {
    fun examinfo(work: String, lost: String, infonumber: String, info: String, label: String, spnumber: String, sptime: String, url: String): Observable<ExamInfoBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).examinfo(work, lost, infonumber, info, label, spnumber, sptime, url)
    }

    fun selectExam(id: String): Observable<SelectExamBean> {
        return RetrofitFactory("http://" +
//                "59.110.162.194:5201" +
                "59.110.161.48:8088" +
                "/").create(ApiService::class.java).selectexam(id)
    }

    fun updateExamInfo(work: String,
                       lost: String,
                       infonumber: String,
                       info: String,
                       label: String,
                       number: String,
                       spnumber: String,
                       sptime: String,
                       url: String): Observable<UpdateExamInfoBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).updateexaminfo(work, lost, infonumber, info, label, number, spnumber, sptime, url)
    }

    fun upload2(s2: List<MultipartBody.Part>): Observable<UploadBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).upload2(s2)
    }
}