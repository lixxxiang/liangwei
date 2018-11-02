package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.presenter.data.repository.InfoEditRepository
import com.android.lixiang.liangwei.presenter.data.repository.RegisterRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.RegisterService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class InfoEditServiceImpl @Inject constructor() : InfoEditService {
    override fun upload2(collectingPictureNames: List<MultipartBody.Part>): Observable<UploadBean> {
        return infoEditRepository.upload2(collectingPictureNames).flatMap(Function<UploadBean, ObservableSource<UploadBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun upload(collectingPictureNames: MultipartBody.Part): Observable<UploadBean> {
        return infoEditRepository.upload(collectingPictureNames).flatMap(Function<UploadBean, ObservableSource<UploadBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun updataIllegalInfo(name: String, phone: String, work: String, iid: String, address: String, area: String, perimeter: String, flag: String, floor: String, type: String, time: String, isdanger: String, isliangwei: String, info: String, url: String, status: String, line: String, number: String): Observable<UpdateIllegalInfoBean> {
        return infoEditRepository.updateIllegalInfo(name,
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
                line, number).flatMap(Function<UpdateIllegalInfoBean, ObservableSource<UpdateIllegalInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun selectIllegal(id: String): Observable<SelectIllegalBean> {
        return infoEditRepository.selectIllegal(id).flatMap(Function<SelectIllegalBean, ObservableSource<SelectIllegalBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun illegalInfo(name: String, phone: String, work: String, iid: String, address: String, area: String, perimeter: String, flag: String, floor: String, type: String, time: String, isdanger: String, isliangwei: String, info: String, url: String, status: String, line: String): Observable<IllegalInfoBean> {
        return infoEditRepository.illegalInfo(name,
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
                line).flatMap(Function<IllegalInfoBean, ObservableSource<IllegalInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }



    @Inject
    lateinit var infoEditRepository: InfoEditRepository
}