package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.data.bean.InfoCollectBean
import com.android.lixiang.liangwei.presenter.data.bean.InsertCollectingPictureBean
import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.liangwei.service.InfoCollectService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject

class InfoCollectServiceImpl @Inject constructor() : InfoCollectService {
    override fun InsertCollectingPictureOffical(informationId: String, collectingPictureNames: List<MultipartBody.Part>): Observable<InsertCollectingPictureBean> {
        return infoCollectRepository.InsertCollectingPictureOffical(informationId, collectingPictureNames).flatMap(Function<InsertCollectingPictureBean, ObservableSource<InsertCollectingPictureBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun testInsertCollectingPicture(informationId: String, collectingPictureNames: MultipartBody.Part): Observable<InsertCollectingPictureBean> {
        return infoCollectRepository.testInsertCollectingPicture2(informationId, collectingPictureNames).flatMap(Function<InsertCollectingPictureBean, ObservableSource<InsertCollectingPictureBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun testInsertCollectingPicture(informationId: String, collectingPictureNames: File): Observable<InsertCollectingPictureBean> {
        return infoCollectRepository.testInsertCollectingPicture(informationId, collectingPictureNames).flatMap(Function<InsertCollectingPictureBean, ObservableSource<InsertCollectingPictureBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun insertCollectingPicture(informationId: String, collectingPictureNames: Array<File>): Observable<InsertCollectingPictureBean> {
        return infoCollectRepository.insertCollectingPicture(informationId, collectingPictureNames).flatMap(Function<InsertCollectingPictureBean, ObservableSource<InsertCollectingPictureBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var infoCollectRepository: InfoCollectRepository


    override fun getData2(area: String, productType: String): Observable<DetailBean> {
        Logger.d(area)
        Logger.d(productType)
        return infoCollectRepository.getDataFromRepository(area, productType).flatMap(Function<DetailBean, ObservableSource<DetailBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    override fun getData3(loginNameId: String, plotNumber: String, insurer: String, identityType: String, idNumber: String, insurerAddress: String, phoneNumber: String, plantingPlace: String, insuranceAmount: String, shape: String): Observable<InfoCollectBean> {
        Logger.d("-------", loginNameId, plotNumber, insurer, identityType, idNumber, insurerAddress, phoneNumber, plantingPlace, insuranceAmount, shape)
        return infoCollectRepository.getDataFromRepository3(loginNameId, plotNumber, insurer, identityType, idNumber, insurerAddress, phoneNumber, plantingPlace, insuranceAmount, shape).flatMap(Function<InfoCollectBean, ObservableSource<InfoCollectBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    private var params: WeakHashMap<String, Any>? = WeakHashMap()
    private var detailBean = DetailBean()

    override fun getData(area: String, productType: String): DetailBean {
//        params!!["area"] = area
//        params!!["productType"] = productType
//
//        RestClient().builder()
//                .url("http://59.110.164.214:8024/global/programming/detail")
//                .params(params!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        detailBean = Gson().fromJson(response, DetailBean::class.java)
//                        Logger.d(response)
//
//                        if (detailBean.message == "success") {
//                        } else {
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {}
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {}
//                })
//                .build()
//                .post()
//
        return detailBean

    }

    override fun test(): String {
        return "FFF"
    }
}