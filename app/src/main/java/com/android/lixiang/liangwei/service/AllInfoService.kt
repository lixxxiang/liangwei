package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface AllInfoService {
//    fun examinfo(work: String, lost: String, infonumber: String, info: String, label: String, spnumber: String, sptime: String): Observable<ExamInfoBean>
//    fun test()
    fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean>
    fun listexaminfo(pagenum: String, pageSize: String): Observable<ListExamInfoBean>
}
