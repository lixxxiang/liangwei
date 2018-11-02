package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.view.InfoCollectView
import com.android.lixiang.liangwei.presenter.view.InfoEditView
import com.android.lixiang.liangwei.presenter.view.LoginView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class InfoEditPresenter @Inject constructor() : BasePresenter<InfoEditView>() {
    @Inject
    lateinit var mInfoEditService: InfoEditService


    fun illegalInfo(name: String, phone: String, work: String, iid: String, address: String, area: String, perimeter: String, flag: String, floor: String, type: String, time: String, isdanger: String, isliangwei: String, info: String, url: String, status: String, line: String) {
        mInfoEditService.illegalInfo(name,
                phone,
                work,
                iid,
                address,
                area,
                perimeter,
                flag,
                floor,
                type,
                time,
                isdanger,
                isliangwei,
                info,
                url,
                status,
                line).execute(object : BaseObserver<IllegalInfoBean>() {
            override fun onNext(t: IllegalInfoBean) {
                super.onNext(t)
                mView.returnIllegalInfo(t)
            }
        }, lifecycleProvider)
    }

    fun updateIllegalInfo(name: String, phone: String, work: String, iid: String, address: String, area: String, perimeter: String, flag: String, floor: String, type: String, time: String, isdanger: String, isliangwei: String, info: String, url: String, status: String, line: String, number: String) {
        mInfoEditService.updataIllegalInfo(name,
                phone,
                work,
                iid,
                address,
                area,
                perimeter,
                flag,
                floor,
                type,
                time,
                isdanger,
                isliangwei,
                info,
                url,
                status,
                line, number).execute(object : BaseObserver<UpdateIllegalInfoBean>() {
            override fun onNext(t: UpdateIllegalInfoBean) {
                super.onNext(t)
                mView.returnUpdateIllegalInfo(t)
            }
        }, lifecycleProvider)
    }


    fun selectIllegal(id: String) {
        mInfoEditService.selectIllegal(id).execute(object : BaseObserver<SelectIllegalBean>() {
            override fun onNext(t: SelectIllegalBean) {
                super.onNext(t)
                mView.returnSelectIllegal(t)
            }
        }, lifecycleProvider)
    }

    fun upload(collectingPictureNames: MultipartBody.Part){
        mInfoEditService.upload(collectingPictureNames).execute(object :BaseObserver<UploadBean>() {
            override fun onNext(t: UploadBean) {
                super.onNext(t)
                Logger.d(t.message)
                mView.returnUpload(t)
            }
        },lifecycleProvider)
    }

    fun upload2(collectingPictureNames: List<MultipartBody.Part>){
        mInfoEditService.upload2(collectingPictureNames).execute(object :BaseObserver<UploadBean>() {
            override fun onNext(t: UploadBean) {
                super.onNext(t)
                Logger.d(t.message)
                mView.returnUpload(t)
            }
        },lifecycleProvider)
    }
}
