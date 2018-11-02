package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.*

interface ApprovalInfomationEditView : BaseView {
    fun test(string:String)
    fun returnexamInfo(examInfoBean: ExamInfoBean)
    fun returnSelectExam(selectExamBean: SelectExamBean)
    fun returnUpdateExamInfo(updateExamInfoBean: UpdateExamInfoBean)
    fun returnUpload(uploadBean: UploadBean)

}