package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.ApprovalInfomationEditRepository
import com.android.lixiang.liangwei.presenter.data.repository.ApprovalInfomationRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.ApprovalInfomationEditService
import com.android.lixiang.liangwei.service.ApprovalInfomationService
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

class ApprovalInfomationServiceImpl @Inject constructor() : ApprovalInfomationService {
    override fun selectExam(id: String): Observable<SelectExamBean> {
        return approvalInfomationRepository.selectExam(id).flatMap(Function<SelectExamBean, ObservableSource<SelectExamBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var approvalInfomationRepository: ApprovalInfomationRepository
}