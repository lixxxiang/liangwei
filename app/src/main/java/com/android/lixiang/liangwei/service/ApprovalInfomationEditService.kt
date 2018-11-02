package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface ApprovalInfomationEditService {
    fun examinfo(work: String, lost: String, infonumber: String, info: String, label: String, spnumber: String, sptime: String, url: String): Observable<ExamInfoBean>
    fun updateexaminfo(work: String, lost: String, infonumber: String, info: String, label: String, number: String, spnumber: String, sptime: String, url: String): Observable<UpdateExamInfoBean>
    fun selectExam(id: String): Observable<SelectExamBean>
    fun upload2(collectingPictureNames: List<MultipartBody.Part>): Observable<UploadBean>

}
