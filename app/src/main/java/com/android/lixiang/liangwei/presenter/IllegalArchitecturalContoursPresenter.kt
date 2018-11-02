package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.IllegalArchitecturalContoursView
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.android.lixiang.liangwei.presenter.view.SetUserNameView
import com.android.lixiang.liangwei.service.IllegalArchitecturalContoursService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.SetUserNameService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class IllegalArchitecturalContoursPresenter @Inject constructor() : BasePresenter<IllegalArchitecturalContoursView>() {
    @Inject
    lateinit var mIllegalArchitecturalContoursService: IllegalArchitecturalContoursService


    fun search(param: String, pagenum: String, pageSize: String) {
        mIllegalArchitecturalContoursService.search(param, pagenum, pageSize).execute(object : BaseObserver<SearchBean>() {
            override fun onNext(t: SearchBean) {
                super.onNext(t)
                mView.returnSearch(t)
            }
        }, lifecycleProvider)
    }
}
