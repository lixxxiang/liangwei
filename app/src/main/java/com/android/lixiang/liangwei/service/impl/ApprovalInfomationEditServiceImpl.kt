package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.ApprovalInfomationEditRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.ApprovalInfomationEditService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class ApprovalInfomationEditServiceImpl @Inject constructor() : ApprovalInfomationEditService {
    override fun upload2(collectingPictureNames: List<MultipartBody.Part>): Observable<UploadBean> {
        return approvalInfomationEditRepository.upload2(collectingPictureNames).flatMap(Function<UploadBean, ObservableSource<UploadBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun updateexaminfo(work: String, lost: String, infonumber: String, info: String, label: String, number: String, spnumber: String, sptime: String, url: String): Observable<UpdateExamInfoBean> {
        return approvalInfomationEditRepository.updateExamInfo(work, lost, infonumber, info, label, number, spnumber, sptime, url).flatMap(Function<UpdateExamInfoBean, ObservableSource<UpdateExamInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun selectExam(id: String): Observable<SelectExamBean> {
        return approvalInfomationEditRepository.selectExam(id).flatMap(Function<SelectExamBean, ObservableSource<SelectExamBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun examinfo(work: String, lost: String, infonumber: String, info: String, label: String, spnumber: String, sptime: String, url: String): Observable<ExamInfoBean> {
        return approvalInfomationEditRepository.examinfo(work, lost, infonumber, info, label, spnumber, sptime, url).flatMap(Function<ExamInfoBean, ObservableSource<ExamInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var approvalInfomationEditRepository: ApprovalInfomationEditRepository
}