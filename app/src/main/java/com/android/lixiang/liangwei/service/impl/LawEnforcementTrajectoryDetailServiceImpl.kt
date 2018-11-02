package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.LawEnforcementTrajectoryDetailRepository
import com.android.lixiang.liangwei.presenter.data.repository.LoginRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LawEnforcementTrajectoryDetailService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class LawEnforcementTrajectoryDetailServiceImpl @Inject constructor() : LawEnforcementTrajectoryDetailService {
//    override fun login(phone: String, password: String): Observable<LoginBean> {
//        return loginRepository.getDataFromRepository(phone, password).flatMap(Function<LoginBean, ObservableSource<LoginBean>> { t ->
//            return@Function Observable.just(t)
//        })
//    }

    @Inject
    lateinit var lawEnforcementTrajectoryDetailRepository: LawEnforcementTrajectoryDetailRepository
}