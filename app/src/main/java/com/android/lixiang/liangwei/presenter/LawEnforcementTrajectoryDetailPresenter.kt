package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectBean
import com.android.lixiang.liangwei.presenter.data.bean.InsertCollectingPictureBean
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.LawEnforcementTrajectoryDetailView
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LawEnforcementTrajectoryDetailService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class LawEnforcementTrajectoryDetailPresenter @Inject constructor(): BasePresenter<LawEnforcementTrajectoryDetailView>(){
    @Inject
    lateinit var mLawEnforcementTrajectoryDetailService : LawEnforcementTrajectoryDetailService

    fun test(s: String){
        mView.returntest(s)
    }
    ////////test
}