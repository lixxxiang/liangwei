package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationEditView
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationView
import com.android.lixiang.liangwei.service.ApprovalInfomationEditService
import com.android.lixiang.liangwei.service.ApprovalInfomationService
import javax.inject.Inject

class ApprovalInfomationPresenter @Inject constructor() : BasePresenter<ApprovalInfomationView>() {
    @Inject
    lateinit var mApprovalInfomationService: ApprovalInfomationService


    fun selectExam(id: String) {
        mApprovalInfomationService.selectExam(id).execute(object : BaseObserver<SelectExamBean>() {
            override fun onNext(t: SelectExamBean) {
                super.onNext(t)
                mView.returnSelectExam(t)
            }
        }, lifecycleProvider)
    }
}
