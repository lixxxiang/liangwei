package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationEditView
import com.android.lixiang.liangwei.service.ApprovalInfomationEditService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import javax.inject.Inject

class ApprovalInfomationEditPresenter @Inject constructor() : BasePresenter<ApprovalInfomationEditView>() {
    @Inject
    lateinit var mApprovalInfomationEditService: ApprovalInfomationEditService


    fun test(s: String) {
        mView.test(s)
    }

    fun examinfo(work: String, lost: String, infonumber: String, info: String, label: String, spnumber: String, sptime: String, url: String) {
        mApprovalInfomationEditService.examinfo(work, lost, infonumber, info, label, spnumber, sptime, url).execute(object : BaseObserver<ExamInfoBean>() {
            override fun onNext(t: ExamInfoBean) {
                super.onNext(t)
                mView.returnexamInfo(t)
            }
        }, lifecycleProvider)
    }

    fun selectExam(id: String) {
        mApprovalInfomationEditService.selectExam(id).execute(object : BaseObserver<SelectExamBean>() {
            override fun onNext(t: SelectExamBean) {
                super.onNext(t)
                mView.returnSelectExam(t)
            }
        }, lifecycleProvider)
    }

    fun updateexaminfo(work: String, lost: String, infonumber: String, info: String, label: String, number: String, spnumber: String, sptime: String, url: String) {
        mApprovalInfomationEditService.updateexaminfo(work, lost, infonumber, info, label, number, spnumber, sptime, url).execute(object : BaseObserver<UpdateExamInfoBean>() {
            override fun onNext(t: UpdateExamInfoBean) {
                super.onNext(t)
                mView.returnUpdateExamInfo(t)
            }
        }, lifecycleProvider)
    }

    fun upload2(collectingPictureNames: List<MultipartBody.Part>){
        mApprovalInfomationEditService.upload2(collectingPictureNames).execute(object :BaseObserver<UploadBean>() {
            override fun onNext(t: UploadBean) {
                super.onNext(t)
                Logger.d(t.message)
                mView.returnUpload(t)
            }
        },lifecycleProvider)
    }
}
