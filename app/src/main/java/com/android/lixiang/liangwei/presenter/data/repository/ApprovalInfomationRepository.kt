package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.ExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SelectExamBean
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean
import io.reactivex.Observable
import javax.inject.Inject

class ApprovalInfomationRepository @Inject constructor() {
    fun selectExam(id:String): Observable<SelectExamBean> {
        return RetrofitFactory("http://" +
//                "59.110.162.194:5201" +
                "59.110.161.48:8088" +
                "/").create(ApiService::class.java).selectexam(id)
    }
}